package niko.nikomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
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


        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.IRON_HAMMER)
                .pattern("I")
                .pattern("S")
                .pattern("S")
                .input('I', Blocks.IRON_BLOCK)
                .input('S', STICK)
                .criterion(hasItem(Blocks.IRON_BLOCK), conditionsFromItem(Blocks.IRON_BLOCK))
                .offerTo(exporter);


    }
}
