package niko.nikomod.block.entity.renderer;


import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import niko.nikomod.block.custom.SmithsAnvil;
import niko.nikomod.block.entity.custom.SmithsAnvilEntity;

import static niko.nikomod.block.custom.SmithsAnvil.getFacingAngle;

public class SmithsAnvilEntityRenderer implements BlockEntityRenderer<SmithsAnvilEntity> {
    public SmithsAnvilEntityRenderer(BlockEntityRendererFactory.Context context){


    }



    @Override
    public void render(SmithsAnvilEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getStack( 0);

        BlockState state = entity.getCachedState();
        float angle = getFacingAngle(state);
        Direction facing = state.get(SmithsAnvil.FACING);


        matrices.push();

// Rotate the whole local space to match block's facing
        matrices.translate(0.5, 0.5, 0.5); // move origin to block center
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-facing.asRotation()));
        matrices.translate(-0.13f, 0.05f, 0.4f);

// Now translate in "north-facing local space"

        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.translate(-0.5f, 0f, -1.075f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
        matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(45));

        itemRenderer.renderItem(stack, ModelTransformationMode.FIXED,
                getLightLevel(entity.getWorld(), entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();


        ItemStack result = entity.getDisplayResult();
        if (!result.isEmpty()) {

            matrices.push();
            matrices.translate(0.5f, 1.0f, 0.5f);
            matrices.scale(0.5f, 0.5f, 0.5f);

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle - 45));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            itemRenderer.renderItem(result, ModelTransformationMode.FIXED,
                    getLightLevel(entity.getWorld(), entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 2);
            matrices.pop();
        }
    }



    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}