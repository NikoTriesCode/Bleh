package niko.nikomod;

import net.fabricmc.api.ModInitializer;

import niko.nikomod.block.ModBlocks;
import niko.nikomod.block.entity.ModBlockEntities;
import niko.nikomod.item.ModItemGroups;
import niko.nikomod.item.ModItems;
import niko.nikomod.screen.ModScreenHandlers;
import niko.nikomod.util.ModEvents;
import niko.nikomod.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NikoMod implements ModInitializer {
	public static final String MOD_ID = "nikomod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModEvents.registerModEvents();
		ModWorldGeneration.generateWorldGen();
		ModScreenHandlers.registerScreenHandlers();
	}
}