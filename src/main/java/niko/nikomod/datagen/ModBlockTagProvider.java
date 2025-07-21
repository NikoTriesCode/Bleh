package niko.nikomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import niko.nikomod.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.STARSILVER_BLOCK)
                .add(ModBlocks.RAW_STARSILVER_BLOCK)
                .add(ModBlocks.STARSILVER_ORE)
                .add(ModBlocks.DEEPSLATE_STARSILVER_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.STARSILVER_BLOCK)
                .add(ModBlocks.RAW_STARSILVER_BLOCK)
                .add(ModBlocks.STARSILVER_ORE)
                .add(ModBlocks.DEEPSLATE_STARSILVER_ORE);


    }
}
