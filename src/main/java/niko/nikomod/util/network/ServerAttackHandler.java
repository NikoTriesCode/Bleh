package niko.nikomod.util.network;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import niko.nikomod.item.custom.HalberdReach;
import niko.nikomod.util.ModTags;

public final class ServerAttackHandler {
    private ServerAttackHandler() {}

    public static void handle(ServerPlayerEntity player, int requestedId) {
        // Must be a halberd in main hand
        if (!player.getMainHandStack().isIn(ModTags.Items.HALBERD_ITEMS)) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] reject: not halberd"), false);
            return;
        }

        final boolean creative = player.isCreative();
        final double baseReach = creative ? 5.0 : 4.5;
        final double reach = baseReach + HalberdReach.EXTRA_REACH;

        var world = player.getWorld();
        Vec3d start = player.getCameraPosVec(1.0F);
        Vec3d look  = player.getRotationVec(1.0F);
        Vec3d end   = start.add(look.multiply(reach));
        Box search  = player.getBoundingBox().stretch(look.multiply(reach)).expand(1.0, 1.0, 1.0);

        // First: try to intersect the specific client-picked entity
        EntityHitResult hit = ProjectileUtil.getEntityCollision(
                world, player, start, end, search,
                e -> e.getId() == requestedId && e instanceof LivingEntity le && e.isAlive() && e.isAttackable()
        );

        // Fallback: if the id mismatched (lag/aim drift), take the first valid living hit along the ray
        if (hit == null) {
            hit = ProjectileUtil.getEntityCollision(
                    world, player, start, end, search,
                    e -> e instanceof LivingEntity le && e.isAlive() && e.isAttackable()
            );
            if (HalberdReach.DEBUG && hit != null) {
                player.sendMessage(Text.literal("[Halberd] server: fallback onto id=" + hit.getEntity().getId()), false);
            }
        }

        if (hit == null) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] reject: server ray miss"), false);
            return;
        }

        LivingEntity living = (LivingEntity) hit.getEntity();

        // If the target is still within vanilla base reach, let vanilla handle it (prevents near-range doubles)
        double dist = Math.sqrt(living.squaredDistanceTo(player));
        if (dist <= baseReach + 1e-6) { // tiny epsilon
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] skip: within base reach (" + String.format("%.2f", dist) + " ≤ " + baseReach + ")"), false);
            return;
        }

        // Optional line-of-sight: make sure no blocks in the way
        if (HalberdReach.REQUIRE_LOS) {
            var blockHit = world.raycast(new RaycastContext(
                    start, hit.getPos(),
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    player
            ));
            if (blockHit.getType() == HitResult.Type.BLOCK) {
                if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] reject: LOS blocked"), false);
                return;
            }
        }

        // Respect vanilla attack cooldown to keep feel consistent
        if (player.getAttackCooldownProgress(0.5f) < 1.0f) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] reject: cooldown"), false);
            return;
        }

        // Attack via vanilla so enchants/crits/attrs apply
        player.attack(living);
        player.resetLastAttackedTicks();
        player.swingHand(Hand.MAIN_HAND, true);
        player.getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);

        if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] ATTACK :D @" + String.format("%.2f", dist) + "b"), false);
    }
}