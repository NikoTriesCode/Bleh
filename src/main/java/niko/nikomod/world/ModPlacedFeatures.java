package niko.nikomod.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import niko.nikomod.NikoMod;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> STARSILVER_ORE_PLACED_SMALL = registryKey("starsilver_ore_placed_small");
    public static final RegistryKey<PlacedFeature> STARSILVER_ORE_PLACED_MEDIUM = registryKey("starsilver_ore_placed_medium");
    public static final RegistryKey<PlacedFeature> STARSILVER_ORE_PLACED_LARGE = registryKey("starsilver_ore_placed_large");
    public static final RegistryKey<PlacedFeature> HELLCHROME_ORE_PLACED_KEY = registryKey("hellchrome_ore_placed");


    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, STARSILVER_ORE_PLACED_SMALL, configuredFeatures.getOrThrow(ModConfiguredFeatures.STARSILVER_ORE_SMALL),
                ModOrePlacement.modifiersWithCount(50,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(-10), YOffset.fixed(500)))
                );
        register(context, STARSILVER_ORE_PLACED_MEDIUM, configuredFeatures.getOrThrow(ModConfiguredFeatures.STARSILVER_ORE_MEDIUM),
                ModOrePlacement.modifiersWithCount(8,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(-30), YOffset.fixed(500)))
        );
        register(context, STARSILVER_ORE_PLACED_LARGE, configuredFeatures.getOrThrow(ModConfiguredFeatures.STARSILVER_ORE_LARGE),
                ModOrePlacement.modifiersWithCount(6,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(-60), YOffset.fixed(200)))
        );

        register(context, HELLCHROME_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.HELLCHROME_ORE_KEY),
                ModOrePlacement.modifiersWithCount(15,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(-10), YOffset.fixed(300)))
        );

    }

    public static RegistryKey<PlacedFeature> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(NikoMod.MOD_ID, name));
    }

    public static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                  RegistryEntry<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }


}
