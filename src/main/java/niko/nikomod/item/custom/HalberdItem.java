package niko.nikomod.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import niko.nikomod.util.ModTags;

import java.util.List;

public class HalberdItem extends Item {

    public HalberdItem(Item.Settings settings) {
        super(settings.component(DataComponentTypes.TOOL, createToolComponent()));
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2
        );
    }
    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }




    /**
     * Two-handed behavior: while a halberd is selected in main hand, keep the OFF_HAND empty.
     * Moves the offhand stack back to inventory; if full, drops it (server-side only).
     */
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;
        if (!(entity instanceof ServerPlayerEntity player)) return;

        // Only while this halberd is actually in main hand
        if (!selected || player.getMainHandStack() != stack) return;

        ItemStack off = player.getOffHandStack();
        if (off.isEmpty()) return;

        // Make a copy to move so we never mutate the live offhand stack mid-insert
        ItemStack moving = off.copy();

        // Try to merge into inventory; if anything remains, drop it instead of "vanishing"
        boolean fullyInserted = player.getInventory().insertStack(moving);
        if (!fullyInserted && !moving.isEmpty()) {
            // 'true' tosses it a bit forward; 'false' drops at feet
            player.dropItem(moving, true);
        }

        // Now clear offhand exactly once
        player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
    }


}
