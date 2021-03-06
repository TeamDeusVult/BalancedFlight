package com.vice.balancedflight;

import com.vice.balancedflight.compat.CuriosCompat;
import com.vice.balancedflight.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeHooks
{
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (Objects.equals(event.getSource().msgId, "flyIntoWall") && Config.disableElytraDamageWithRings.get()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntityLiving();
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (Objects.equals(event.getSource().msgId, "fall") && Config.disableFallDamageWithRings.get()) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntityLiving();
                if (CuriosCompat.AllowedFlightModes(player, false) != CuriosCompat.FlightMode.None)
                    event.setCanceled(true);
            }
        }
    }
}
