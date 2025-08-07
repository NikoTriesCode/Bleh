package niko.nikomod.recipes;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import niko.nikomod.NikoMod;

public class ModRecipes {


    public static final RecipeType<SanvilShapedRecipe> SANVIL_SHAPED_TYPE = Registry.register(Registries.RECIPE_TYPE, Identifier.of(
            NikoMod.MOD_ID, "smiths_anvil_shaped"), new RecipeType<SanvilShapedRecipe>() {
        @Override
        public String toString() {
            return "smiths_anvil_shaped";
        }
    });
    public static final RecipeSerializer<SanvilShapedRecipe> SANVIL_SHAPED_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(
            NikoMod.MOD_ID, "smiths_anvil_shaped"), new SanvilShapedRecipe.Serializer());



    public static void registerRecipes(){
        NikoMod.LOGGER.info("Registering Custom Recipes for " + NikoMod.MOD_ID);
    }
}
