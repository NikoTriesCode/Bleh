package niko.nikomod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import niko.nikomod.block.entity.ModBlockEntities;
import niko.nikomod.block.entity.renderer.SmithsAnvilEntityRenderer;

public class NikoModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockEntityRendererFactories.register(ModBlockEntities.SANVIL_BE, SmithsAnvilEntityRenderer::new);

    }
}
