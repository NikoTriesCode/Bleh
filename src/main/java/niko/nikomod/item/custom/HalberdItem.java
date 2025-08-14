package niko.nikomod.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (world.isClient || !(entity instanceof PlayerEntity player)) return;

        // If THIS item is in offhand, eject it (prevents offhand equip)
        if (player.getOffHandStack() == stack) {
            // try to move to main inventory first
            ItemStack copy = stack.copy();
            player.getInventory().offHand.set(0, ItemStack.EMPTY);
            if (!player.getInventory().insertStack(copy)) {
                player.dropItem(copy, true);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            // How far you want to reach
            double reach = 4.5D;

            // Player’s eye position
            Vec3d eyePos = user.getCameraPosVec(1.0F);
            Vec3d lookVec = user.getRotationVec(1.0F);
            Vec3d reachEnd = eyePos.add(lookVec.multiply(reach));

            // Find target entity
            EntityHitResult entityHit = raycastEntities(world, user, eyePos, reachEnd, reach);
            if (entityHit != null && entityHit.getEntity() instanceof LivingEntity living) {
                living.damage(world.getDamageSources().playerAttack(user), 8.0F); // your halberd damage
                stack.damage(1, user, EquipmentSlot.MAINHAND);
            }
        }

        user.swingHand(hand);
        return TypedActionResult.success(stack, world.isClient());
    }

    private EntityHitResult raycastEntities(World world, PlayerEntity player, Vec3d start, Vec3d end, double maxDistance) {
        EntityHitResult hitResult = ProjectileUtil.getEntityCollision(
                world,
                player,
                start,
                end,
                player.getBoundingBox().stretch(player.getRotationVec(1.0F).multiply(maxDistance)).expand(1.0D, 1.0D, 1.0D),
                e -> !e.isSpectator() && e.isAlive() && e.canHit()
        );
        return hitResult;
    }

}
