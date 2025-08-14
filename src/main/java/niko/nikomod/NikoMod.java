package niko.nikomod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import niko.nikomod.block.ModBlocks;
import niko.nikomod.block.entity.ModBlockEntities;
import niko.nikomod.item.ModItemGroups;
import niko.nikomod.item.ModItems;
import niko.nikomod.item.custom.HalberdReach;
import niko.nikomod.recipes.ModRecipes;
import niko.nikomod.screen.ModScreenHandlers;
import niko.nikomod.util.ModEvents;
import niko.nikomod.util.ModTags;
import niko.nikomod.util.network.HalberdAttackPayload;
import niko.nikomod.util.network.NetIds;
import niko.nikomod.util.network.ServerAttackHandler;
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


		PayloadTypeRegistry.playC2S().register(HalberdAttackPayload.ID, HalberdAttackPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(HalberdAttackPayload.ID, (payload, ctx) -> {
			var player = ctx.player();
			player.getServerWorld().getServer().execute(() ->
					niko.nikomod.util.network.ServerAttackHandler.handle(player, payload.entityId())
			);
		});

	}
}