package niko.nikomod.recipes;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.collection.DefaultedList;

public class SanvilRecipeInput implements RecipeInput {
    private final ItemStack hammer;                       // slot 0
    private final DefaultedList<ItemStack> grid;          // slots 1..9

    public SanvilRecipeInput(ItemStack hammer, DefaultedList<ItemStack> grid) {
        this.hammer = hammer == null ? ItemStack.EMPTY : hammer.copy();
        this.grid = grid;
    }

    public static SanvilRecipeInput of(ItemStack hammer, RecipeInputInventory inv) {
        DefaultedList<ItemStack> list = DefaultedList.ofSize(9, ItemStack.EMPTY);
        for (int i = 0; i < 9; i++) list.set(i, inv.getStack(i).copy());
        return new SanvilRecipeInput(hammer, list);
    }

    public ItemStack getHammer() { return hammer; }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot == 0) return hammer;
        int idx = slot - 1;
        return (idx >= 0 && idx < 9) ? grid.get(idx) : ItemStack.EMPTY;
    }

    @Override
    public int getSize() { return 10; } // 0 = hammer, 1..9 = grid
}
