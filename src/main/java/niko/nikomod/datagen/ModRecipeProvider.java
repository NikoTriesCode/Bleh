package niko.nikomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import niko.nikomod.block.ModBlocks;
import niko.nikomod.item.ModItems;
import niko.nikomod.util.ModTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.item.Items.*;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        List<ItemConvertible> STARSILVER_SMELTABLES = List.of(ModItems.RAW_STARSILVER, ModBlocks.STARSILVER_ORE, ModBlocks.DEEPSLATE_STARSILVER_ORE);
        offerSmelting(exporter, STARSILVER_SMELTABLES, RecipeCategory.MISC, ModItems.STARSILVER_INGOT, 0.7f, 250, "starsilver");
        offerBlasting(exporter, STARSILVER_SMELTABLES, RecipeCategory.MISC, ModItems.STARSILVER_INGOT, 0.7f, 125, "starsilver");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.STARSILVER_INGOT, RecipeCategory.DECORATIONS, ModBlocks.STARSILVER_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_STARSILVER, RecipeCategory.DECORATIONS, ModBlocks.RAW_STARSILVER_BLOCK);


        List<ItemConvertible> HELLCHROME_SMELTABLES = List.of(ModItems.RAW_HELLCHROME, ModBlocks.HELLCHROME_ORE);
        offerSmelting(exporter, HELLCHROME_SMELTABLES, RecipeCategory.MISC, ModItems.HELLCHROME_INGOT, 1.4f, 180, "hellchrome");
        offerBlasting(exporter, HELLCHROME_SMELTABLES, RecipeCategory.MISC, ModItems.HELLCHROME_INGOT, 1.4f, 90, "hellchrome");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.HELLCHROME_INGOT, RecipeCategory.DECORATIONS, ModBlocks.HELLCHROME_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_HELLCHROME, RecipeCategory.DECORATIONS, ModBlocks.RAW_HELLCHROME_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.IRON_HAMMER)
                .pattern("I")
                .pattern("S")
                .pattern("S")
                .input('I', Blocks.IRON_BLOCK)
                .input('S', STICK)
                .criterion(hasItem(Blocks.IRON_BLOCK), conditionsFromItem(Blocks.IRON_BLOCK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GOLD_HAMMER)
                .pattern("I")
                .pattern("S")
                .pattern("S")
                .input('I', Blocks.GOLD_BLOCK)
                .input('S', STICK)
                .criterion(hasItem(Blocks.IRON_BLOCK), conditionsFromItem(Blocks.IRON_BLOCK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COPPER_HAMMER)
                .pattern("I")
                .pattern("S")
                .pattern("S")
                .input('I', Blocks.COPPER_BLOCK)
                .input('S', STICK)
                .criterion(hasItem(Blocks.IRON_BLOCK), conditionsFromItem(Blocks.IRON_BLOCK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.DIAMOND_HAMMER)
                .pattern("I")
                .pattern("S")
                .pattern("S")
                .input('I', Blocks.DIAMOND_BLOCK)
                .input('S', STICK)
                .criterion(hasItem(Blocks.IRON_BLOCK), conditionsFromItem(Blocks.IRON_BLOCK))
                .offerTo(exporter);

        offerNetheriteUpgradeRecipe(exporter, ModItems.DIAMOND_HAMMER, RecipeCategory.TOOLS, ModItems.NETHERITE_HAMMER);


        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.IRON_SHEET_METAL)
                .input(IRON_INGOT)
                .input(IRON_INGOT)
                .input(ModTags.Items.HAMMER_ITEMS)
                .criterion(hasItem(IRON_BLOCK), conditionsFromItem(IRON_BLOCK))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COPPER_SHEET_METAL)
                .input(COPPER_INGOT)
                .input(COPPER_INGOT)
                .input(ModTags.Items.HAMMER_ITEMS)
                .criterion(hasItem(COPPER_BLOCK), conditionsFromItem(COPPER_BLOCK))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GOLD_SHEET_METAL)
                .input(GOLD_INGOT)
                .input(GOLD_INGOT)
                .input(ModTags.Items.HAMMER_ITEMS)
                .criterion(hasItem(GOLD_BLOCK), conditionsFromItem(GOLD_BLOCK))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.NETHERITE_SHEET_METAL)
                .input(NETHERITE_INGOT)
                .input(ModTags.Items.HAMMER_ITEMS)
                .criterion(hasItem(NETHERITE_INGOT), conditionsFromItem(NETHERITE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DIAMOND_SHEET_METAL, 4)
                .pattern(" D ")
                .pattern("DID")
                .pattern(" D ")
                .input('D', DIAMOND)
                .input('I', ModItems.IRON_SHEET_METAL)
                .criterion(hasItem(DIAMOND), conditionsFromItem(DIAMOND))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.STARSILVER_PICKAXE)
                .pattern("SSS")
                .pattern(" T ")
                .pattern(" T ")
                .input('S', ModItems.STARSILVER_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.STARSILVER_INGOT), conditionsFromItem(ModItems.STARSILVER_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STARSILVER_AXE)
                .pattern("SS")
                .pattern("ST")
                .pattern(" T")
                .input('S', ModItems.STARSILVER_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.STARSILVER_INGOT), conditionsFromItem(ModItems.STARSILVER_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.STARSILVER_HOE)
                .pattern("SS")
                .pattern("T ")
                .pattern("T ")
                .input('S', ModItems.STARSILVER_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.STARSILVER_INGOT), conditionsFromItem(ModItems.STARSILVER_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.STARSILVER_SHOVEL)
                .pattern("S")
                .pattern("T")
                .pattern("T")
                .input('S', ModItems.STARSILVER_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.STARSILVER_INGOT), conditionsFromItem(ModItems.STARSILVER_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STARSILVER_SWORD)
                .pattern("S")
                .pattern("S")
                .pattern("T")
                .input('S', ModItems.STARSILVER_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.STARSILVER_INGOT), conditionsFromItem(ModItems.STARSILVER_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HELLCHROME_PICKAXE)
                .pattern("HHH")
                .pattern(" T ")
                .pattern(" T ")
                .input('H', ModItems.HELLCHROME_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.HELLCHROME_INGOT), conditionsFromItem(ModItems.HELLCHROME_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HELLCHROME_AXE)
                .pattern("HH")
                .pattern("HT")
                .pattern(" T")
                .input('H', ModItems.HELLCHROME_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.HELLCHROME_INGOT), conditionsFromItem(ModItems.HELLCHROME_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HELLCHROME_HOE)
                .pattern("HH")
                .pattern("T ")
                .pattern("T ")
                .input('H', ModItems.HELLCHROME_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.HELLCHROME_INGOT), conditionsFromItem(ModItems.HELLCHROME_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.HELLCHROME_SHOVEL)
                .pattern("H")
                .pattern("T")
                .pattern("T")
                .input('H', ModItems.HELLCHROME_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.HELLCHROME_INGOT), conditionsFromItem(ModItems.HELLCHROME_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.HELLCHROME_SWORD)
                .pattern("H")
                .pattern("H")
                .pattern("T")
                .input('H', ModItems.HELLCHROME_INGOT)
                .input('T', STICK)
                .criterion(hasItem(ModItems.HELLCHROME_INGOT), conditionsFromItem(ModItems.HELLCHROME_INGOT))
                .offerTo(exporter);

    }
}
