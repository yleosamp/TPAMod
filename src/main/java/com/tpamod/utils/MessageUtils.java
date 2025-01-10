package com.tpamod.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerPlayer;

public class MessageUtils {
    public static void sendCombatMessage(ServerPlayer player) {
        player.displayClientMessage(new TextComponent("§c§l⚔ Você está em combate! ⚔"), true); // true = actionbar (meio da tela)
    }
    
    public static void sendTpaRequest(ServerPlayer target, ServerPlayer sender) {
        target.sendMessage(
            new TextComponent("\n§6§l━━━━━━━━━━ §e§lPEDIDO DE TPA §6§l━━━━━━━━━━\n\n")
            .append("§f" + sender.getName().getString() + "§e deseja se teleportar até você!\n\n")
            .append(createAcceptButton(sender))
            .append("\n§6§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"),
            target.getUUID()
        );
    }
    
    private static Component createAcceptButton(ServerPlayer sender) {
        return new TextComponent("           §2§l[ ACEITAR ]")
            .withStyle(style -> style
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + sender.getName().getString()))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent("§aClique para aceitar o teleporte")))
                .withBold(true)
            );
    }
} 