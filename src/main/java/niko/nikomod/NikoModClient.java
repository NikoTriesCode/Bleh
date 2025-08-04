package niko.nikomod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import niko.nikomod.block.entity.ModBlockEntities;
import niko.nikomod.block.entity.renderer.SmithsAnvilEntityRenderer;
import niko.nikomod.screen.ModScreenHandlers;
import niko.nikomod.screen.custom.SanvilScreen;

public class NikoModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockEntityRendererFactories.register(ModBlockEntities.SANVIL_BE, SmithsAnvilEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.SANVIL_SCREEN_HANDLER, SanvilScreen::new);

    }
}
