package com.tpamod.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.tpamod.TPAMod;

@Mod.EventBusSubscriber(modid = TPAMod.MOD_ID)
public class CommandRegistration {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        
        dispatcher.register(Commands.literal("tpa")
            .then(TPACommand.register()));
            
        dispatcher.register(Commands.literal("tpaccept")
            .then(TPAcceptCommand.register()));
    }
}
