package niko.nikomod.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import niko.nikomod.block.entity.ImplementedInventory;
import niko.nikomod.block.entity.ModBlockEntities;
import niko.nikomod.screen.custom.SanvilScreenHandler;
import org.jetbrains.annotations.Nullable;


public class SmithsAnvilEntity extends BlockEntity
        implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {

    // === Inventory: ONLY the hammer slot (index 0) ===
    private static final int HAMMER_SLOT = 0;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public SmithsAnvilEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SANVIL_BE, pos, state);
    }


    private static final String TAG_DISPLAY_RESULT = "DisplayResult";
    private ItemStack displayResult = ItemStack.EMPTY;

    public ItemStack getDisplayResult() { return displayResult; }

    public void setDisplayResult(ItemStack stack) {
        this.displayResult = stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
        markDirty();
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
        }
    }


    // Expose to the handler
    public Inventory getInventory() { return this; }

    // ImplementedInventory
    @Override
    public DefaultedList<ItemStack> getItems() { return inventory; }

    // UI factory bits
    @Override
    public Text getDisplayName() {
        return Text.translatable("block.nikomod.smithsanvil");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SanvilScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
    }

    // Persist hammer
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.writeNbt(nbt, lookup);
        Inventories.writeNbt(nbt, getItems(), lookup); // your hammer slot
        if (!displayResult.isEmpty()) {
            nbt.put(TAG_DISPLAY_RESULT, displayResult.encode(lookup));
        } else {
            // Optional: ensure tag is absent if empty
            nbt.remove(TAG_DISPLAY_RESULT);
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.readNbt(nbt, lookup);
        Inventories.readNbt(nbt, getItems(), lookup);

        if (nbt.contains(TAG_DISPLAY_RESULT, NbtElement.COMPOUND_TYPE)) {
            this.displayResult = ItemStack.fromNbt(lookup, nbt.getCompound(TAG_DISPLAY_RESULT))
                    .orElse(ItemStack.EMPTY);
        } else {
            this.displayResult = ItemStack.EMPTY;
        }
    }

    // Client sync
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }


    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    // ExtendedScreenHandlerFactory payload (we send the BlockPos)
    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }

    // Optional: ensure clients update when hammer changes
    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }
}