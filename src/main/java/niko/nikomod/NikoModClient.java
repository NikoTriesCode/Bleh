package niko.nikomod;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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
import niko.nikomod.util.network.ReachCode.HalberdAttackPayload;

public class NikoModClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.SANVIL_BE, SmithsAnvilEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.SANVIL_SCREEN_HANDLER, SanvilScreen::new);
    }
}
