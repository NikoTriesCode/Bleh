package niko.nikomod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import niko.nikomod.NikoMod;
import niko.nikomod.item.custom.HalberdItem;
import niko.nikomod.item.custom.HammerItem;

import javax.tools.Tool;

public class ModItems {



    public static final Item STARSILVER_INGOT = registerItem("starsilver_ingot", new Item(new Item.Settings()));
    public static final Item RAW_STARSILVER = registerItem("raw_starsilver", new Item(new Item.Settings()));
    public static final Item RAW_HELLCHROME = registerItem("raw_hellchrome", new Item(new Item.Settings()));
    public static final Item HELLCHROME_INGOT = registerItem("hellchrome_ingot", new Item(new Item.Settings()));

    public static final Item IRON_SHEET_METAL = registerItem("iron_sheet", new Item(new Item.Settings()));
    public static final Item GOLD_SHEET_METAL = registerItem("gold_sheet", new Item(new Item.Settings()));
    public static final Item COPPER_SHEET_METAL = registerItem("copper_sheet", new Item(new Item.Settings()));
    public static final Item DIAMOND_SHEET_METAL = registerItem("diamond_sheet", new Item(new Item.Settings()));
    public static final Item NETHERITE_SHEET_METAL = registerItem("netherite_sheet", new Item(new Item.Settings().fireproof()));

    public static final Item STARSILVER_SWORD = registerItem(
            "starsilver_sword",
            new SwordItem(ModToolMaterials.STARSILVER, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.STARSILVER, 3, -2.4F))));

    public static final Item STARSILVER_SHOVEL = registerItem(
            "starsilver_shovel",
            new ShovelItem(ModToolMaterials.STARSILVER, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.STARSILVER, 1.5F, -3.0F)))
    );

    public static final Item STARSILVER_PICKAXE = registerItem(
            "starsilver_pickaxe",
            new PickaxeItem(ModToolMaterials.STARSILVER, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.STARSILVER, 1.0F, -2.8F)))
    );

    public static final Item STARSILVER_AXE = registerItem(
            "starsilver_axe",
            new AxeItem(ModToolMaterials.STARSILVER, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.STARSILVER, 6.0F, -3.1F)))
    );

    public static final Item STARSILVER_HOE = registerItem(
            "starsilver_hoe",
            new HoeItem(ModToolMaterials.STARSILVER, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.STARSILVER, -2.0F, -1.0F)))
    );

    public static final Item HELLCHROME_SWORD = registerItem(
            "hellchrome_sword", new SwordItem(ModToolMaterials.HELLCHROME, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.HELLCHROME, 3, -2.4F)))
    );
    public static final Item HELLCHROME_SHOVEL = registerItem(
            "hellchrome_shovel",
            new ShovelItem(ModToolMaterials.HELLCHROME, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.HELLCHROME, 1.5F, -3.0F)))
    );
    public static final Item HELLCHROME_PICKAXE = registerItem(
            "hellchrome_pickaxe",
            new PickaxeItem(ModToolMaterials.HELLCHROME, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.HELLCHROME, 1.0F, -2.8F)))
    );
    public static final Item HELLCHROME_AXE = registerItem(
            "hellchrome_axe", new AxeItem(ModToolMaterials.HELLCHROME, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.HELLCHROME, 6.0F, -3.0F)))
    );
    public static final Item HELLCHROME_HOE = registerItem(
            "hellchrome_hoe", new HoeItem(ModToolMaterials.HELLCHROME, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.HELLCHROME, 0.0F, -3.0F)))
    );


    public static final Item IRON_HALBERD = registerItem(
            "iron_halberd",
            new HalberdItem(new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.IRON, 10.0F, -3.8F)).maxDamage(712))
    );
    public static final Item GOLD_HALBERD = registerItem(
            "gold_halberd",
            new HalberdItem(new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.GOLD, 10.0F, -3.8F)).maxDamage(200))
    );
    public static final Item DIAMOND_HALBERD = registerItem(
            "diamond_halberd",
            new HalberdItem(new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 10.0F, -3.8F)).maxDamage(1580))
    );
    public static final Item NETHERITE_HALBERD = registerItem(
            "netherite_halberd",
            new HalberdItem(new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.NETHERITE, 10.0F, -3.8F)).maxDamage(2400))
    );
    public static final Item STARSILVER_HALBERD = registerItem(
            "starsilver_halberd",
            new HalberdItem(new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.STARSILVER, 10.0F, -3.8F)).maxDamage(990))
    );
    public static final Item HELLCHROME_HALBERD = registerItem(
            "hellchrome_halberd",
            new HalberdItem(new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.HELLCHROME, 10.0F, -3.8F)).maxDamage(440))
    );




    public static final Item IRON_HAMMER = registerItem("iron_hammer", new HammerItem(new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.IRON, 6, -3.5F)).maxDamage(72)));
    public static final Item COPPER_HAMMER = registerItem("copper_hammer", new HammerItem(new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.IRON, 5, -3.5F)).maxDamage(48)));
    public static final Item GOLD_HAMMER = registerItem("gold_hammer", new HammerItem(new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.GOLD, 6, -3.5F)).maxDamage(24)));
    public static final Item DIAMOND_HAMMER = registerItem("diamond_hammer", new HammerItem(new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.DIAMOND, 6, -3.5F)).maxDamage(512)));
    public static final Item NETHERITE_HAMMER = registerItem("netherite_hammer", new HammerItem(new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.NETHERITE, 6, -3.5F)).maxDamage(1024).fireproof()));


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

            fabricItemGroupEntries.add(IRON_SHEET_METAL);
            fabricItemGroupEntries.add(COPPER_SHEET_METAL);
            fabricItemGroupEntries.add(GOLD_SHEET_METAL);
            fabricItemGroupEntries.add(DIAMOND_SHEET_METAL);
            fabricItemGroupEntries.add(NETHERITE_SHEET_METAL);

            fabricItemGroupEntries.add(STARSILVER_SWORD);
            fabricItemGroupEntries.add(STARSILVER_SHOVEL);
            fabricItemGroupEntries.add(STARSILVER_PICKAXE);
            fabricItemGroupEntries.add(STARSILVER_AXE);
            fabricItemGroupEntries.add(STARSILVER_HOE);

            fabricItemGroupEntries.add(HELLCHROME_SWORD);
            fabricItemGroupEntries.add(HELLCHROME_SHOVEL);
            fabricItemGroupEntries.add(HELLCHROME_PICKAXE);
            fabricItemGroupEntries.add(HELLCHROME_AXE);
            fabricItemGroupEntries.add(HELLCHROME_HOE);


            fabricItemGroupEntries.add(IRON_HALBERD);
            fabricItemGroupEntries.add(GOLD_HALBERD);
            fabricItemGroupEntries.add(DIAMOND_HALBERD);
            fabricItemGroupEntries.add(NETHERITE_HALBERD);
            fabricItemGroupEntries.add(STARSILVER_HALBERD);
            fabricItemGroupEntries.add(HELLCHROME_HALBERD);

            fabricItemGroupEntries.add(IRON_HAMMER);
            fabricItemGroupEntries.add(COPPER_HAMMER);
            fabricItemGroupEntries.add(GOLD_HAMMER);
            fabricItemGroupEntries.add(DIAMOND_HAMMER);
            fabricItemGroupEntries.add(NETHERITE_HAMMER);

        });
    }
}
