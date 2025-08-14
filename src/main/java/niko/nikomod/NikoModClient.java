package niko.nikomod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import niko.nikomod.block.entity.ModBlockEntities;
import niko.nikomod.block.entity.renderer.SmithsAnvilEntityRenderer;
import niko.nikomod.item.custom.HalberdReach;
import niko.nikomod.screen.ModScreenHandlers;
import niko.nikomod.screen.custom.SanvilScreen;
import niko.nikomod.util.ModTags;
import niko.nikomod.util.network.HalberdAttackPayload;
import niko.nikomod.util.network.NetIds;

public class NikoModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockEntityRendererFactories.register(ModBlockEntities.SANVIL_BE, SmithsAnvilEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.SANVIL_SCREEN_HANDLER, SanvilScreen::new);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null) return;
            var player = client.player;
            if (player == null || client.world == null) return;

            // Only act when the attack key was pressed this tick
            if (!client.options.attackKey.wasPressed()) return;

            ItemStack held = player.getMainHandStack();
            if (!held.isIn(ModTags.Items.HALBERD_ITEMS)) return; // only for your halberds

            // Base reach from abilities (no mapping hazards)
            final boolean creative = player.getAbilities().creativeMode;
            final double baseReach = creative ? 5.0 : 4.5;
            final double reach = baseReach + HalberdReach.EXTRA_REACH;

            // Raycast farther than vanilla
            Vec3d eye  = player.getCameraPosVec(1.0F);
            Vec3d look = player.getRotationVec(1.0F);
            Vec3d end  = eye.add(look.multiply(reach));

            EntityHitResult hit = raycastEntities(client, eye, end, reach);
            if (hit == null) {
                if (HalberdReach.DEBUG) player.sendMessage(Text.literal("[Halberd] client: no target ≤ " + reach + "b"), true);
                return;
            }

            if (HalberdReach.DEBUG) {
                double dist = Math.sqrt(hit.getEntity().squaredDistanceTo(player));
                player.sendMessage(Text.literal("[Halberd] client: send id=" + hit.getEntity().getId() + " @" + String.format("%.2f", dist) + "b"), true);
            }

            // Feedback + send payload
            player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
            ClientPlayNetworking.send(new HalberdAttackPayload(hit.getEntity().getId()));
        });
    }

    private static EntityHitResult raycastEntities(MinecraftClient client, Vec3d start, Vec3d end, double maxDistance) {
        var player = client.player;
        var world  = client.world;
        Box search = player.getBoundingBox()
                .stretch(player.getRotationVec(1.0F).multiply(maxDistance))
                .expand(1.0, 1.0, 1.0);

        return ProjectileUtil.getEntityCollision(
                world, player, start, end, search,
                e -> e instanceof LivingEntity le && le.isAlive() && e.isAttackable() && !e.isSpectator()
        );
    }
}