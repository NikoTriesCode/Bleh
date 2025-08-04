package niko.nikomod.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import niko.nikomod.NikoMod;
import niko.nikomod.block.ModBlocks;
import niko.nikomod.block.entity.custom.SmithsAnvilEntity;

public class ModBlockEntities {
    public static final BlockEntityType<SmithsAnvilEntity> SANVIL_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(NikoMod.MOD_ID, "sanvil_be"),
                    BlockEntityType.Builder.create(SmithsAnvilEntity::new, ModBlocks.SMITHSANVIL).build(null));

    public static void registerBlockEntities(){
        NikoMod.LOGGER.info("Registering Block Entities for " + NikoMod.MOD_ID);
    }
}
