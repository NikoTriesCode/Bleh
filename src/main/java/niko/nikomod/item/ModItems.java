package niko.nikomod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import niko.nikomod.NikoMod;
import niko.nikomod.item.custom.HammerItem;

public class ModItems {
    public static final Item STARSILVER_INGOT = registerItem("starsilver_ingot", new Item(new Item.Settings()));
    public static final Item RAW_STARSILVER = registerItem("raw_starsilver", new Item(new Item.Settings()));
    public static final Item RAW_HELLCHROME = registerItem("raw_hellchrome", new Item(new Item.Settings()));
    public static final Item HELLCHROME_INGOT = registerItem("hellchrome_ingot", new Item(new Item.Settings()));


    public static final Item IRON_HAMMER = registerItem("iron_hammer", new HammerItem(ToolMaterials.IRON, new Item.Settings().maxDamage(72).recipeRemainder(ModItems.IRON_HAMMER).attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.IRON, 6, -3.5F))));
    public static final Item COPPER_HAMMER = registerItem("copper_hammer", new HammerItem(ToolMaterials.IRON, new Item.Settings().maxDamage(48).recipeRemainder(ModItems.COPPER_HAMMER).attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.IRON, 5, -3.5F))));
    public static final Item GOLD_HAMMER = registerItem("gold_hammer", new HammerItem(ToolMaterials.GOLD, new Item.Settings().maxDamage(24).attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.GOLD, 6, -3.5F))));
    public static final Item DIAMOND_HAMMER = registerItem("diamond_hammer", new HammerItem(ToolMaterials.DIAMOND, new Item.Settings().maxDamage(512).attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.DIAMOND, 6, -3.5F))));
    public static final Item NETHERITE_HAMMER = registerItem("netherite_hammer", new HammerItem(ToolMaterials.NETHERITE, new Item.Settings().recipeRemainder(ModItems.NETHERITE_HAMMER).maxDamage(1024).attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.NETHERITE, 6, -3.5F))));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(NikoMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        NikoMod.LOGGER.info("Registering Mod Items for " + NikoMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(RAW_STARSILVER);
            fabricItemGroupEntries.add(STARSILVER_INGOT);
            fabricItemGroupEntries.add(RAW_HELLCHROME);
            fabricItemGroupEntries.add(HELLCHROME_INGOT);
            fabricItemGroupEntries.add(IRON_HAMMER);
            fabricItemGroupEntries.add(COPPER_HAMMER);
            fabricItemGroupEntries.add(GOLD_HAMMER);
            fabricItemGroupEntries.add(DIAMOND_HAMMER);
            fabricItemGroupEntries.add(NETHERITE_HAMMER);

        });
    }
}
