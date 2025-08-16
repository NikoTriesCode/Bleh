package niko.nikomod.util.network.ReachCode;

import niko.nikomod.item.custom.HalberdReach;
import niko.nikomod.util.ModTags;

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

public final class ExtendedAttackHandler {
    private ExtendedAttackHandler() {}

    public static void handle(ServerPlayerEntity player, int targetId) {
        // 0) quick prints so we always know we entered
        if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] server: recv id=" + targetId), false);

        // must be a halberd in MAIN hand
        if (!player.getMainHandStack().isIn(ModTags.Items.HALBERD_ITEMS)) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] server: not a halberd"), false);
            return;
        }

        var world = player.getServerWorld();
        var target = world.getEntityById(targetId);
        if (!(target instanceof LivingEntity living) || !living.isAlive() || !target.isAttackable() || target.isSpectator()) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] server: bad target"), false);
            return;
        }

        // 1) distances
        double baseMelee = player.isCreative() ? 6.0 : 3.0;           // server authority
        double maxMelee  = baseMelee + HalberdReach.EXTRA;
        double dist      = Math.sqrt(living.squaredDistanceTo(player));
        if (HalberdReach.DEBUG) player.sendMessage(Text.literal(String.format("[Halberd] server: dist=%.2f base=%.2f max=%.2f", dist, baseMelee, maxMelee)), false);

        if (dist <= baseMelee + 1e-6) {                               // vanilla will/should handle close hits
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] server: within base -> ignore"), false);
            return;
        }
        if (dist > maxMelee + 1e-6) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] server: beyond max -> reject"), false);
            return;
        }

        // 2) ray along the player's look, and require we actually hit THIS entity
        Vec3d start = player.getCameraPosVec(1.0F);
        Vec3d look  = player.getRotationVec(1.0F);
        Vec3d end   = start.add(look.multiply(maxMelee));
        Box search  = player.getBoundingBox().stretch(look.multiply(maxMelee)).expand(1,1,1);

        EntityHitResult hit = ProjectileUtil.getEntityCollision(
                world, player, start, end, search,
                e -> e.getId() == target.getId() && e instanceof LivingEntity le && e.isAlive() && e.isAttackable()
        );
        if (hit == null) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] server: ray miss to that id"), false);
            return;
        }

        // 3) line-of-sight (optional)
        if (HalberdReach.REQUIRE_LOS) {
            var los = world.raycast(new RaycastContext(
                    start, hit.getPos(),
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    player
            ));
            if (los.getType() == HitResult.Type.BLOCK) {
                if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] server: blocked by LOS"), false);
                return;
            }
        }

        // 4) cooldown (informational only — do NOT block)
        float cps = player.getAttackCooldownProgress(0.5f);
        if (HalberdReach.DEBUG) player.sendMessage(Text.literal(String.format("[Halberd] server: cooldown=%.2f (not blocking)", cps)), false);

        // 5) do a normal attack (vanilla handles scaling, crits, enchants, knockback, item damage, and cooldown reset)
        player.attack(living);
        player.swingHand(Hand.MAIN_HAND, true);

        if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] server: ATTACK :D"), false);
    }
}