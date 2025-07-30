package niko.nikomod.util;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

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




    }
}