package niko.nikomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import niko.nikomod.block.ModBlocks;
import niko.nikomod.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_STARSILVER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STARSILVER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_STARSILVER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STARSILVER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HELLCHROME_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HELLCHROME_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_HELLCHROME_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.STARSILVER_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_STARSILVER, Models.GENERATED);
        itemModelGenerator.register(ModItems.HELLCHROME_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_HELLCHROME, Models.GENERATED);

        itemModelGenerator.register(ModItems.IRON_SHEET_METAL,Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLD_SHEET_METAL,Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_SHEET_METAL,Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_SHEET_METAL,Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_SHEET_METAL,Models.GENERATED);

        itemModelGenerator.register(ModItems.IRON_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GOLD_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DIAMOND_HAMMER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.NETHERITE_HAMMER, Models.HANDHELD);

    }
}