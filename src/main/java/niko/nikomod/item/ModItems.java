package niko.nikomod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import niko.nikomod.NikoMod;

public class ModItems {
    public static final Item STARSILVER_INGOT = registerItem("starsilver_ingot", new Item(new Item.Settings()));
    public static final Item RAW_STARSILVER = registerItem("raw_starsilver", new Item(new Item.Settings()));


    public static final Item IRON_HAMMER = registerItem("iron_hammer", new Item(new Item.Settings().maxDamage(125)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(NikoMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        NikoMod.LOGGER.info("Registering Mod Items for " + NikoMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(RAW_STARSILVER);
            fabricItemGroupEntries.add(STARSILVER_INGOT);
            fabricItemGroupEntries.add(IRON_HAMMER);

        });
    }
}
