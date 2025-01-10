package com.tpamod.events;

import com.tpamod.TPAManager;
import com.tpamod.TPAMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TPAMod.MOD_ID)
public class CombatEvents {
    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            TPAManager.tagPlayerInCombat(event.getEntity().getUUID());
        }
        
        if (event.getSource().getEntity() instanceof ServerPlayer) {
            TPAManager.tagPlayerInCombat(event.getSource().getEntity().getUUID());
        }
    }
}
