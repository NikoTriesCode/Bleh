package niko.nikomod.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import niko.nikomod.world.ModPlacedFeatures;

public class ModOreGeneration {
    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.STARSILVER_ORE_PLACED_SMALL);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.STARSILVER_ORE_PLACED_MEDIUM);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.STARSILVER_ORE_PLACED_LARGE);

        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.HELLCHROME_ORE_PLACED_KEY);

    //For specific Biomes
        // BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.*insert-biome-here-1*, ... BiomeKeys.*insert-biome-here-n),
        //                  GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.*insert-ore*);

    }
}
