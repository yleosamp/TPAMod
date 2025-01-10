package com.tpamod.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.tpamod.TPAManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

public class TPACommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.argument("target", EntityArgument.player())
            .executes(context -> {
                ServerPlayer sender = context.getSource().getPlayerOrException();
                ServerPlayer target = EntityArgument.getPlayer(context, "target");
                
                if (sender == target) {
                    sender.sendMessage(new TextComponent("Você não pode enviar TPA para si mesmo!"), sender.getUUID());
                    return 0;
                }
                
                if (TPAManager.isInCombat(sender.getUUID())) {
                    sender.sendMessage(new TextComponent("Você está em combate!"), sender.getUUID());
                    return 0;
                }
                
                TPAManager.createRequest(sender.getUUID(), target.getUUID());
                
                Component acceptMessage = new TextComponent("§aClique aqui para aceitar")
                    .withStyle(style -> style
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + sender.getName().getString()))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent("Clique para aceitar o TPA")))
                    );
                
                target.sendMessage(new TextComponent("§6" + sender.getName().getString() + " quer se teleportar até você. ").append(acceptMessage), target.getUUID());
                sender.sendMessage(new TextComponent("§6Pedido de TPA enviado para " + target.getName().getString()), sender.getUUID());
                
                return 1;
            });
    }
}
