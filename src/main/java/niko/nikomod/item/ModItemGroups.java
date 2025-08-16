package niko.nikomod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import niko.nikomod.NikoMod;
import niko.nikomod.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup NIKOS_STUFF = Registry.register(Registries.ITEM_GROUP, Identifier.of(NikoMod.MOD_ID, "nikos_stuff"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.STARSILVER_INGOT))
                    .displayName(Text.translatable("itemgroup.nikomod.nikos_stuff"))
                    .entries((displayContext, entries) -> {
                        //Items
                        entries.add(ModItems.RAW_STARSILVER);
                        entries.add(ModItems.STARSILVER_INGOT);
                        entries.add(ModItems.RAW_HELLCHROME);
                        entries.add(ModItems.HELLCHROME_INGOT);

                        entries.add(ModItems.COPPER_SHEET_METAL);
                        entries.add(ModItems.GOLD_SHEET_METAL);
                        entries.add(ModItems.IRON_SHEET_METAL);
                        entries.add(ModItems.DIAMOND_SHEET_METAL);
                        entries.add(ModItems.NETHERITE_SHEET_METAL);
                        //Blocks
                        entries.add(ModBlocks.STARSILVER_ORE);
                        entries.add(ModBlocks.DEEPSLATE_STARSILVER_ORE);
                        entries.add(ModBlocks.RAW_STARSILVER_BLOCK);
                        entries.add(ModBlocks.STARSILVER_BLOCK);
                        entries.add(ModBlocks.HELLCHROME_ORE);
                        entries.add(ModBlocks.RAW_HELLCHROME_BLOCK);
                        entries.add(ModBlocks.HELLCHROME_BLOCK);
                        //Armor
                        //Weapons
                        entries.add(ModItems.STARSILVER_SWORD);
                        entries.add(ModItems.STARSILVER_SHOVEL);
                        entries.add(ModItems.STARSILVER_PICKAXE);
                        entries.add(ModItems.STARSILVER_AXE);
                        entries.add(ModItems.STARSILVER_HOE);

                        entries.add(ModItems.HELLCHROME_SWORD);
                        entries.add(ModItems.HELLCHROME_SHOVEL);
                        entries.add(ModItems.HELLCHROME_PICKAXE);
                        entries.add(ModItems.HELLCHROME_AXE);
                        entries.add(ModItems.HELLCHROME_HOE);

                        entries.add(ModItems.IRON_HALBERD);
                        entries.add(ModItems.GOLD_HALBERD);
                        entries.add(ModItems.DIAMOND_HALBERD);
                        entries.add(ModItems.NETHERITE_HALBERD);
                        entries.add(ModItems.STARSILVER_HALBERD);
                        entries.add(ModItems.HELLCHROME_HALBERD);

                        //Useful Items

                        entries.add(ModItems.COPPER_HAMMER);
                        entries.add(ModItems.GOLD_HAMMER);
                        entries.add(ModItems.IRON_HAMMER);
                        entries.add(ModItems.DIAMOND_HAMMER);
                        entries.add(ModItems.NETHERITE_HAMMER);
                        //Mechanisms and Custom Interactable
                        entries.add(ModBlocks.SMITHSANVIL);
                    })
                    .build());


    public static void registerItemGroups(){
        NikoMod.LOGGER.info("Registering Item Groups for" + NikoMod.MOD_ID);
    }
}
