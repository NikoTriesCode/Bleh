package niko.nikomod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import niko.nikomod.block.ModBlocks;
import niko.nikomod.block.entity.ModBlockEntities;
import niko.nikomod.item.ModItemGroups;
import niko.nikomod.item.ModItems;
import niko.nikomod.recipes.ModRecipes;
import niko.nikomod.screen.ModScreenHandlers;
import niko.nikomod.util.ModEvents;
import niko.nikomod.util.network.ReachCode.ExtendedAttackHandler;
import niko.nikomod.util.network.ReachCode.HalberdAttackPayload;
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
		ModRecipes.registerRecipes();

		LOGGER.info("[Halberd] NikoMod.onInitialize() START");
		PayloadTypeRegistry.playC2S().register(HalberdAttackPayload.ID, HalberdAttackPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(HalberdAttackPayload.ID, (payload, ctx) -> {
			LOGGER.info("[Halberd] SERVER recv packet id={}, target={}", HalberdAttackPayload.ID.id(), payload.targetId());
			var player = ctx.player();
			ctx.server().execute(() -> ExtendedAttackHandler.handle(player, payload.targetId()));
		});
		LOGGER.info("[Halberd] SERVER channel registered {}", HalberdAttackPayload.ID.id());
	}
}