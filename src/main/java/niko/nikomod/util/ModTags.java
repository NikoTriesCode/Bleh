package niko.nikomod.util;

import net.fabricmc.fabric.mixin.tag.TagKeyMixin;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import niko.nikomod.NikoMod;

public class ModTags {
    public static class Blocks{
        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(NikoMod.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> HAMMER_ITEMS = createTag("hammer_items");


        private static TagKey<Item> createTag(String name){
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(NikoMod.MOD_ID, name));
        }
    }
}
