package niko.nikomod.util;

import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.EntityTypeTags;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;

public class ModEvents {
    public static void registerModEvents() {

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(entity instanceof LivingEntity livingEntity){
                if(player.getMainHandStack().isIn(ModTags.Items.STARSILVER_ITEMS) && entity.getType().isIn(EntityTypeTags.SENSITIVE_TO_SMITE)){
                    livingEntity.damage(world.getDamageSources().playerAttack(player), 8.0f);
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 2, false, false));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200,2, false, false ));
                }
                return ActionResult.PASS;
            }
            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if(entity instanceof LivingEntity livingEntity){
                if(player.getMainHandStack().isIn(ModTags.Items.HELLCHROME_ITEMS) && entity.isOnFire()){
                    livingEntity.damage(world.getDamageSources().playerAttack(player), 12.0f);
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 2, false, false));
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200,2, false, false ));
                }
                return ActionResult.PASS;
            }
            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (hand == Hand.OFF_HAND && player.getMainHandStack().isIn(ModTags.Items.HALBERD_ITEMS)) {
                return TypedActionResult.fail(player.getStackInHand(hand));
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });


        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (hand == Hand.OFF_HAND && player.getMainHandStack().isIn(ModTags.Items.HALBERD_ITEMS)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.OFF_HAND && player.getMainHandStack().isIn(ModTags.Items.HALBERD_ITEMS)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (hand == Hand.OFF_HAND && player.getMainHandStack().isIn(ModTags.Items.HALBERD_ITEMS)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.OFF_HAND && player.getMainHandStack().isIn(ModTags.Items.HALBERD_ITEMS)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });


    }
}