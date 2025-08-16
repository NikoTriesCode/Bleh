package niko.nikomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import niko.nikomod.item.ModItems;
import niko.nikomod.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.HAMMER_ITEMS)
                .add(ModItems.IRON_HAMMER)
                .add(ModItems.GOLD_HAMMER)
                .add(ModItems.COPPER_HAMMER)
                .add(ModItems.DIAMOND_HAMMER)
                .add(ModItems.NETHERITE_HAMMER);

        getOrCreateTagBuilder(ModTags.Items.HEAVY_WEAPONS)
                .addTag(ModTags.Items.HALBERD_ITEMS);

        getOrCreateTagBuilder(ModTags.Items.SHEET_METAL)
                .add(ModItems.IRON_SHEET_METAL)
                .add(ModItems.GOLD_SHEET_METAL)
                .add(ModItems.COPPER_SHEET_METAL)
                .add(ModItems.DIAMOND_SHEET_METAL)
                .add(ModItems.NETHERITE_SHEET_METAL);

        getOrCreateTagBuilder(ModTags.Items.STARSILVER_ITEMS)
                .add(ModItems.STARSILVER_INGOT)
                .add(ModItems.STARSILVER_AXE)
                .add(ModItems.STARSILVER_SHOVEL)
                .add(ModItems.STARSILVER_SWORD)
                .add(ModItems.STARSILVER_PICKAXE)
                .add(ModItems.STARSILVER_HOE)
                .add(ModItems.STARSILVER_HALBERD);

        getOrCreateTagBuilder(ModTags.Items.HELLCHROME_ITEMS)
                .add(ModItems.HELLCHROME_INGOT)
                .add(ModItems.HELLCHROME_AXE)
                .add(ModItems.HELLCHROME_SHOVEL)
                .add(ModItems.HELLCHROME_SWORD)
                .add(ModItems.HELLCHROME_PICKAXE)
                .add(ModItems.HELLCHROME_HOE)
                .add(ModItems.HELLCHROME_HALBERD);

        getOrCreateTagBuilder(ModTags.Items.HALBERD_ITEMS)
                .add(ModItems.IRON_HALBERD)
                .add(ModItems.GOLD_HALBERD)
                .add(ModItems.DIAMOND_HALBERD)
                .add(ModItems.NETHERITE_HALBERD)
                .add(ModItems.STARSILVER_HALBERD)
                .add(ModItems.HELLCHROME_HALBERD);


        getOrCreateTagBuilder(ItemTags.SWORD_ENCHANTABLE)
                .addTag(ModTags.Items.HEAVY_WEAPONS);
        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .addTag(ModTags.Items.HEAVY_WEAPONS);


        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.STARSILVER_SWORD)
                .add(ModItems.HELLCHROME_SWORD);

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.STARSILVER_AXE)
                .add(ModItems.HELLCHROME_AXE);

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.STARSILVER_PICKAXE)
                .add(ModItems.HELLCHROME_PICKAXE);

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.STARSILVER_SHOVEL)
                .add(ModItems.HELLCHROME_SHOVEL);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.STARSILVER_HOE)
                .add(ModItems.HELLCHROME_HOE);
    }
}
