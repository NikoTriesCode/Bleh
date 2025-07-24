package niko.nikomod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
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

        getOrCreateTagBuilder(ModTags.Items.SHEET_METAL)
                .add(ModItems.IRON_SHEET_METAL)
                .add(ModItems.GOLD_SHEET_METAL)
                .add(ModItems.COPPER_SHEET_METAL)
                .add(ModItems.DIAMOND_SHEET_METAL)
                .add(ModItems.NETHERITE_SHEET_METAL);
    }
}
