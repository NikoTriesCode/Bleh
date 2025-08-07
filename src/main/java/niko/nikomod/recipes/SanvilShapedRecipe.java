package niko.nikomod.recipes;

import com.mojang.serialization.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public record SanvilShapedRecipe(
        Ingredient[] slots,                 // size 9 (grid)
        @Nullable Ingredient hammerReq,     // null = no specific hammer needed
        boolean requireAnyHammer,           // true = hammer slot must be non-empty
        ItemStack output
) implements Recipe<SanvilRecipeInput> {

    @Override
    public boolean matches(SanvilRecipeInput input, World world) {
        if (world.isClient()) return false;

        ItemStack hammer = input.getStackInSlot(0);
        if (requireAnyHammer && hammer.isEmpty()) return false;
        if (hammerReq != null && !hammerReq.test(hammer)) return false;

        // Try normal + mirrored
        return matchesGrid(input, false) || matchesGrid(input, true);
    }

    private boolean matchesGrid(SanvilRecipeInput input, boolean mirror) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int col = mirror ? (2 - c) : c;
                Ingredient need = slots[r * 3 + col];
                ItemStack stack = input.getStackInSlot(1 + r * 3 + c); // 1..9 = grid
                if (need == Ingredient.EMPTY) {
                    if (!stack.isEmpty()) return false;
                } else if (!need.test(stack)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override public ItemStack craft(SanvilRecipeInput in, RegistryWrapper.WrapperLookup l) { return output.copy(); }
    @Override public boolean fits(int w, int h) { return true; }
    @Override public ItemStack getResult(RegistryWrapper.WrapperLookup l) { return output; }
    @Override public RecipeSerializer<?> getSerializer() { return ModRecipes.SANVIL_SHAPED_SERIALIZER; }
    @Override public RecipeType<?> getType() { return ModRecipes.SANVIL_SHAPED_TYPE; }

    // ---- Serializer ----
    public static class Serializer implements RecipeSerializer<SanvilShapedRecipe> {

        public static final MapCodec<SanvilShapedRecipe> CODEC = new MapCodec<SanvilShapedRecipe>() {
            @Override
            public <T> DataResult<SanvilShapedRecipe> decode(DynamicOps<T> ops, MapLike<T> map) {
                // --- result (required) ---
                T resultNode = map.get("result");
                if (resultNode == null) return DataResult.error(() -> "Missing 'result'");
                ItemStack output = ItemStack.CODEC.parse(ops, resultNode)
                        .result().orElse(ItemStack.EMPTY);
                if (output.isEmpty()) return DataResult.error(() -> "'result' decoded to empty ItemStack");

                // --- hammer (optional) ---
                Ingredient hammerReq = null;
                T hammerNode = map.get("hammer");
                if (hammerNode != null) {
                    hammerReq = Ingredient.ALLOW_EMPTY_CODEC.parse(ops, hammerNode).result().orElse(null);
                }

                // --- require_hammer (optional, default false) ---
                boolean requireAnyHammer = false;
                T requireNode = map.get("require_hammer");
                if (requireNode != null) {
                    requireAnyHammer = Codec.BOOL.parse(ops, requireNode).result().orElse(false);
                }

                Ingredient[] grid = new Ingredient[9];
                Arrays.fill(grid, Ingredient.EMPTY);

                // Try pattern+key first
                T patternNode = map.get("pattern");
                T keyNode = map.get("key");
                if (patternNode != null && keyNode != null) {
                    List<String> pattern = Codec.list(Codec.STRING).parse(ops, patternNode)
                            .result().orElse(List.of());
                    if (pattern.size() != 3 || pattern.stream().anyMatch(s -> s.length() != 3)) {
                        return DataResult.error(() -> "'pattern' must be exactly 3 strings of length 3");
                    }
                    Map<String, Ingredient> key = Codec.unboundedMap(Codec.STRING, Ingredient.ALLOW_EMPTY_CODEC)
                            .parse(ops, keyNode).result().orElse(Map.of());

                    for (int r = 0; r < 3; r++) {
                        String row = pattern.get(r);
                        for (int c = 0; c < 3; c++) {
                            char ch = row.charAt(c);
                            if (ch == ' ') { grid[r * 3 + c] = Ingredient.EMPTY; continue; }
                            Ingredient ing = key.get(String.valueOf(ch));
                            if (ing == null) return DataResult.error(() -> "No key for '" + ch + "'");
                            grid[r * 3 + c] = ing;
                        }
                    }
                    return DataResult.success(new SanvilShapedRecipe(grid, hammerReq, requireAnyHammer, output));
                }

                // Otherwise use "slots"
                T slotsNode = map.get("slots");
                if (slotsNode == null) {
                    return DataResult.error(() -> "Provide either 'pattern'+'key' or 'slots'");
                }
                Map<String, Ingredient> slots = Codec.unboundedMap(Codec.STRING, Ingredient.ALLOW_EMPTY_CODEC)
                        .parse(ops, slotsNode).result().orElse(Map.of());

                for (var e : slots.entrySet()) {
                    int idx;
                    try { idx = Integer.parseInt(e.getKey()); }
                    catch (NumberFormatException ex) { return DataResult.error(() -> "Slot keys must be '1'..'9'"); }
                    if (idx < 1 || idx > 9) return DataResult.error(() -> "Slot out of range: " + idx);
                    grid[idx - 1] = e.getValue();
                }
                return DataResult.success(new SanvilShapedRecipe(grid, hammerReq, requireAnyHammer, output));
            }

            @Override
            public <T> RecordBuilder<T> encode(SanvilShapedRecipe r, DynamicOps<T> ops, RecordBuilder<T> b) {
                // We only need to emit fields required for runtime reloading; choose the minimal set.
                b.add("result", ItemStack.CODEC.encodeStart(ops, r.output()));
                if (r.hammerReq() != null) {
                    b.add("hammer", Ingredient.ALLOW_EMPTY_CODEC.encodeStart(ops, r.hammerReq()));
                }
                if (r.requireAnyHammer()) {
                    b.add("require_hammer", ops.createBoolean(true));
                }
                // don’t serialize the grid back (pattern/slots) since MC doesn’t need to round‑trip recipes.
                return b;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return Stream.of("result", "hammer", "require_hammer", "pattern", "key", "slots")
                        .map(ops::createString);
            }
        };

        public static final PacketCodec<RegistryByteBuf, SanvilShapedRecipe> STREAM_CODEC =
                PacketCodec.ofStatic((buf, r) -> {
                    // write
                    buf.writeBoolean(r.hammerReq() != null);
                    if (r.hammerReq() != null) Ingredient.PACKET_CODEC.encode(buf, r.hammerReq());
                    buf.writeBoolean(r.requireAnyHammer());
                    for (int i = 0; i < 9; i++) Ingredient.PACKET_CODEC.encode(buf, r.slots()[i]);
                    ItemStack.PACKET_CODEC.encode(buf, r.output());
                }, buf -> {
                    // read
                    Ingredient hammerReq = buf.readBoolean() ? Ingredient.PACKET_CODEC.decode(buf) : null;
                    boolean requireAnyHammer = buf.readBoolean();
                    Ingredient[] grid = new Ingredient[9];
                    for (int i = 0; i < 9; i++) grid[i] = Ingredient.PACKET_CODEC.decode(buf);
                    ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
                    return new SanvilShapedRecipe(grid, hammerReq, requireAnyHammer, output);
                });

        @Override public MapCodec<SanvilShapedRecipe> codec() { return CODEC; }
        @Override public PacketCodec<RegistryByteBuf, SanvilShapedRecipe> packetCodec() { return STREAM_CODEC; }
    }
}
