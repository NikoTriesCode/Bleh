package niko.nikomod.screen.custom;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import niko.nikomod.block.ModBlocks;
import niko.nikomod.block.entity.custom.SmithsAnvilEntity;
import niko.nikomod.recipes.ModRecipes;
import niko.nikomod.recipes.SanvilRecipeInput;
import niko.nikomod.screen.ModScreenHandlers;
import niko.nikomod.screen.gui_slots.HammerSlot;
import niko.nikomod.util.ModTags;

import java.util.concurrent.atomic.AtomicReference;

public class SanvilScreenHandler extends AbstractRecipeScreenHandler<CraftingRecipeInput, CraftingRecipe> {

    // Replace your constants with these (ENDs are exclusive)
    private static final int RESULT_SLOT       = 0;
    private static final int GRID_START        = 1;   // 1..9
    private static final int GRID_END          = 10;  // exclusive
    private static final int HAMMER_SLOT       = 10;  // single slot
    private static final int PLAYER_START      = 11;  // 27 slots
    private static final int PLAYER_END        = 38;  // exclusive
    private static final int HOTBAR_START      = 38;  // 9 slots
    private static final int HOTBAR_END        = 47;  // exclusive
    private final RecipeInputInventory input = new CraftingInventory(this, 3, 3);
    private final CraftingResultInventory result = new CraftingResultInventory();
    private final SmithsAnvilEntity blockEntity;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    private boolean isUsableHammer(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (!stack.isIn(ModTags.Items.HAMMER_ITEMS)) return false;
        // If every hammer is damageable, enforce it:
        if (!stack.isDamageable()) return false;
        return stack.getDamage() < stack.getMaxDamage();
    }




    public SanvilScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.SANVIL_SCREEN_HANDLER, syncId);
        AtomicReference<SmithsAnvilEntity> beRef = new AtomicReference<>();
        context.run((world, pos) -> {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof SmithsAnvilEntity smithsAnvil) {
                beRef.set(smithsAnvil);
            }
        });
        this.blockEntity = beRef.get();
        this.context = context;
        this.player = playerInventory.player;
        this.addSlot(new Slot(blockEntity.getInventory(), 0, 18, 35) {
            @Override public boolean canInsert(ItemStack stack) { return stack.isIn(ModTags.Items.HAMMER_ITEMS); }
            @Override public int getMaxItemCount() { return 1; }

            private void fireUpdate() { SanvilScreenHandler.this.onContentChanged(this.inventory); }

            @Override public void markDirty() { super.markDirty(); fireUpdate(); }
            @Override public void onTakeItem(PlayerEntity player, ItemStack stack) { super.onTakeItem(player, stack); fireUpdate(); }
            @Override public void setStack(ItemStack stack) { super.setStack(stack); fireUpdate(); }
        });
        this.addSlot(new CraftingResultSlot(this.player, this.input, this.result, RESULT_SLOT, 138, 35) {
            @Override
            public void onTakeItem(PlayerEntity player, ItemStack crafted) {
                super.onTakeItem(player, crafted);

                // Damage the hammer in the block entity's slot 0
                Inventory hammerInv = blockEntity.getInventory();
                ItemStack hammer = hammerInv.getStack(0);

                if (!hammer.isEmpty() && hammer.isDamageable()) {
                    // Manual durability change to avoid version-specific overloads
                    int dmg = hammer.getDamage() + 1;
                    if (dmg >= hammer.getMaxDamage()) {
                        hammer.decrement(1); // breaks
                    } else {
                        hammer.setDamage(dmg);
                    }
                    hammerInv.markDirty();
                    // (Optional) break animation:
                    if (player instanceof ServerPlayerEntity spe) {
                        spe.currentScreenHandler.sendContentUpdates(); // sync UI
                    }
                }
                if (!player.getWorld().isClient) {
                    World world = player.getWorld();
                    BlockPos pos = blockEntity.getPos();
                    world.playSound(
                            /* player */ null,                 // null = broadcast to all nearby
                            pos,
                            SoundEvents.BLOCK_ANVIL_USE,       // pick any sound you like
                            SoundCategory.BLOCKS,
                            1.0f,                               // volume
                            0.9f + world.random.nextFloat() * 0.2f // slight pitch variance
                    );
                }
                // Recompute output after inputs + hammer changed
                onContentChanged(input);
            }
        });


        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.input, j + i * 3, 44 + j * 18, 17 + i * 18));
            }
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }


    }




    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack ret = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasStack()) return ItemStack.EMPTY;

        ItemStack stack = slot.getStack();
        ret = stack.copy();

        // Taking result -> move to player inventory
        if (index == RESULT_SLOT) {
            this.context.run((world, pos) -> stack.getItem().onCraftByPlayer(stack, world, player));
            if (!this.insertItem(stack, PLAYER_START, HOTBAR_END, true)) return ItemStack.EMPTY;
            slot.onQuickTransfer(stack, ret);
            slot.onTakeItem(player, stack);
        }
        // From hammer slot -> player
        else if (index == HAMMER_SLOT) {
            if (!this.insertItem(stack, PLAYER_START, HOTBAR_END, false)) return ItemStack.EMPTY;
        }

        // From player inv/hotbar
        else if (index >= PLAYER_START && index < HOTBAR_END) {
            if (stack.isIn(ModTags.Items.HAMMER_ITEMS)) {
                if (!this.insertItem(stack, HAMMER_SLOT, HAMMER_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(stack, GRID_START, GRID_END, false)) {
                // swap between main ↔ hotbar
                if (index < PLAYER_END) {
                    if (!this.insertItem(stack, HOTBAR_START, HOTBAR_END, false)) return ItemStack.EMPTY;
                } else if (!this.insertItem(stack, PLAYER_START, PLAYER_END, false)) return ItemStack.EMPTY;
            }
        }
        // From grid -> player
        else if (index >= GRID_START && index < GRID_END) {
            if (!this.insertItem(stack, PLAYER_START, HOTBAR_END, false)) return ItemStack.EMPTY;
        }
        // Anything else
        else if (!this.insertItem(stack, PLAYER_START, HOTBAR_END, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.setStack(ItemStack.EMPTY);
        else slot.markDirty();

        if (stack.getCount() == ret.getCount()) return ItemStack.EMPTY;

        slot.onTakeItem(player, stack);
        return ret;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.SMITHSANVIL);
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {

    }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(RecipeEntry<CraftingRecipe> recipe) {
        return false;
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 11;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return null;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
        if (!player.getWorld().isClient && blockEntity != null) {
            blockEntity.setDisplayResult(ItemStack.EMPTY);
        }
    }

    @Override
    public void onContentChanged(Inventory inv) {
        super.onContentChanged(inv);
        if (player.getWorld().isClient) return;

        ItemStack hammer = blockEntity.getInventory().getStack(0);
        ItemStack out = ItemStack.EMPTY;

        if (isUsableHammer(hammer)) {
            var rm = player.getServer().getRecipeManager();
            var sin = SanvilRecipeInput.of(hammer, this.input);

            var m = rm.getFirstMatch(ModRecipes.SANVIL_SHAPED_TYPE, sin, player.getWorld());
            if (m.isPresent()) out = m.get().value().craft(sin, player.getWorld().getRegistryManager());
            // (optional) fallback to your uniform type if you support both...
        }

        this.result.setStack(RESULT_SLOT, out);
        // If you do the live-preview render:
        if (blockEntity != null) blockEntity.setDisplayResult(out);
        sendContentUpdates();
    }

}
