package niko.nikomod.screen.gui_slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import niko.nikomod.util.ModTags;

public class HammerSlot extends Slot {
    public HammerSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isIn(ModTags.Items.HAMMER_ITEMS);
    }
}
