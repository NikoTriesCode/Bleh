package niko.nikomod.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import niko.nikomod.NikoMod;
import niko.nikomod.block.ModBlocks;
import niko.nikomod.util.ModTags;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> STARSILVER_ORE_SMALL = registryKey("starsilver_ore_small");
    public static final RegistryKey<ConfiguredFeature<?, ?>> STARSILVER_ORE_MEDIUM = registryKey("starsilver_ore_medium");
    public static final RegistryKey<ConfiguredFeature<?, ?>> STARSILVER_ORE_LARGE = registryKey("starsilver_ore_large");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HELLCHROME_ORE_KEY = registryKey("hellchrome_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context){

        RuleTest stoneReplaceable = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceable = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);

        //unused for now
        RuleTest endReplaceable = new BlockMatchRuleTest(Blocks.END_STONE);
        //

        List<OreFeatureConfig.Target> overworldStarsilverOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceable, ModBlocks.STARSILVER_ORE.getDefaultState()));
                        OreFeatureConfig.createTarget(deepslateReplaceable, ModBlocks.DEEPSLATE_STARSILVER_ORE.getDefaultState());

        List<OreFeatureConfig.Target> netherHellchromeOre =
                List.of(OreFeatureConfig.createTarget(netherReplaceable, ModBlocks.HELLCHROME_ORE.getDefaultState()));

        register(context, STARSILVER_ORE_SMALL, Feature.ORE, new OreFeatureConfig(overworldStarsilverOres, 4));
        register(context, STARSILVER_ORE_MEDIUM, Feature.ORE, new OreFeatureConfig(overworldStarsilverOres, 8));
        register(context, STARSILVER_ORE_LARGE, Feature.ORE, new OreFeatureConfig(overworldStarsilverOres, 10));
        register(context, HELLCHROME_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherHellchromeOre, 12));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(NikoMod.MOD_ID, name));
    }


    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
