package niko.nikomod.mixin;

import niko.nikomod.item.custom.HalberdReach;
import niko.nikomod.util.ModTags;
import niko.nikomod.util.network.ReachCode.HalberdAttackPayload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public ClientPlayerEntity player;
    @Shadow @Nullable public ClientWorld world;

    // Optional: tiny log so you know left-click went through
    @Inject(method = "doAttack", at = @At("HEAD"))
    private void nikomod$logDoAttackHead(CallbackInfoReturnable<Boolean> cir) {
        if (HalberdReach.DEBUG && player != null) {
            player.sendMessage(Text.literal("[Halberd] client: doAttack()"), false);
        }
    }

    @Inject(method = "doAttack", at = @At("RETURN"))
    private void nikomod$extendedHalberdAttack(CallbackInfoReturnable<Boolean> cir) {
        // If vanilla landed a hit, we're done.
        if (cir.getReturnValue()) return;
        if (player == null || world == null) return;

        // Only when a halberd is in main hand
        if (!player.getMainHandStack().isIn(ModTags.Items.HALBERD_ITEMS)) return;

        // Server authority uses 3.0/6.0; client gate uses ~3.3 in survival to avoid double-sends
        boolean creative = player.getAbilities().creativeMode;
        double baseServer     = creative ? 6.0 : 3.0;
        double baseClientGate = creative ? 6.0 : 3.3;
        double reach          = baseServer + HalberdReach.EXTRA;

        // Long entity ray along view
        Vec3d start = player.getCameraPosVec(1.0F);
        Vec3d look  = player.getRotationVec(1.0F);
        Vec3d end   = start.add(look.multiply(reach));
        Box search  = player.getBoundingBox().stretch(look.multiply(reach)).expand(1, 1, 1);

        EntityHitResult hit = ProjectileUtil.getEntityCollision(
                world, player, start, end, search,
                e -> e instanceof LivingEntity le && le.isAlive() && e.isAttackable() && !e.isSpectator()
        );
        if (hit == null) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] client: no extended target"), false);
            return;
        }

        double dist = Math.sqrt(hit.getEntity().squaredDistanceTo(player));
        if (dist <= baseClientGate + 1e-6) return; // let vanilla handle close hits

        if (!ClientPlayNetworking.canSend(HalberdAttackPayload.ID)) {
            if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] client: cannot send payload"), false);
            return;
        }

        // feedback + send
        player.swingHand(player.getActiveHand());
        if (HalberdReach.DEBUG) {
            player.sendMessage(Text.literal("[Halberd] client: SEND id=" + hit.getEntity().getId()
                    + " @" + String.format("%.2f", dist) + "b"), false);
        }
        ClientPlayNetworking.send(new HalberdAttackPayload(hit.getEntity().getId()));
    }
}