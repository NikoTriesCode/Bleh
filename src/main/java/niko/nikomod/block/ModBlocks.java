package niko.nikomod.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import niko.nikomod.NikoMod;
import niko.nikomod.block.custom.SmithsAnvil;


public class ModBlocks {

    public static final Block STARSILVER_BLOCK = registerBlock("starsilver_block",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE_GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));

    public static final Block HELLCHROME_BLOCK = registerBlock("hellchrome_block",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.BRIGHT_RED).instrument(NoteBlockInstrument.BASS)
            ));


    public static final Block RAW_STARSILVER_BLOCK = registerBlock("raw_starsilver_block",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.RAW_IRON_PINK).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(3.5F, 2.0F)));

    public static final Block RAW_HELLCHROME_BLOCK = registerBlock("raw_hellchrome_block",
            new Block(AbstractBlock.Settings.create().mapColor(MapColor.RAW_IRON_PINK).instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool().strength(3.0F, 3.5F)));



    public static final Block STARSILVER_ORE = registerBlock("starsilver_ore",
            new ExperienceDroppingBlock(
                    ConstantIntProvider.create(0),
                    AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY)
                            .instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.5F, 2.0F)
            ));

    public static final Block DEEPSLATE_STARSILVER_ORE = registerBlock("deepslate_starsilver_ore",
            new ExperienceDroppingBlock(
                    ConstantIntProvider.create(0),
                    AbstractBlock.Settings.copyShallow(STARSILVER_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(5.0F, 2.5F).sounds(BlockSoundGroup.DEEPSLATE)
            ));

    public static final Block HELLCHROME_ORE = registerBlock("hellchrome_ore",
            new ExperienceDroppingBlock(
                    ConstantIntProvider.create(0),
                    AbstractBlock.Settings.create().mapColor(MapColor.BRIGHT_RED)
                            .instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.0F, 3.5F)
            ));



    public static final Block SMITHSANVIL = registerBlock("smithsanvil", new SmithsAnvil(AbstractBlock.Settings.create()
            .mapColor(MapColor.IRON_GRAY)
            .requiresTool()
            .strength(5.0F, 1200.0F)
            .sounds(BlockSoundGroup.ANVIL)
            .pistonBehavior(PistonBehavior.BLOCK)
    ));





    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(NikoMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, net.minecraft.block.Block block){
        Registry.register(Registries.ITEM, Identifier.of(NikoMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        NikoMod.LOGGER.info("Registering Mod Blocks for " + NikoMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModBlocks.STARSILVER_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.RAW_STARSILVER_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.STARSILVER_ORE);
            fabricItemGroupEntries.add(ModBlocks.DEEPSLATE_STARSILVER_ORE);
            fabricItemGroupEntries.add(ModBlocks.RAW_HELLCHROME_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.HELLCHROME_ORE);
            fabricItemGroupEntries.add(ModBlocks.HELLCHROME_BLOCK);
        });
    }
}
