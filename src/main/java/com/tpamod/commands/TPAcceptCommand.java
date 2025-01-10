package com.tpamod.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.tpamod.TPAManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class TPAcceptCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.argument("player", EntityArgument.player())
            .executes(context -> {
                ServerPlayer acceptor = context.getSource().getPlayerOrException();
                ServerPlayer requester = EntityArgument.getPlayer(context, "player");
                
                TPAManager.TPARequest request = TPAManager.getRequest(acceptor.getUUID());
                
                if (request == null || !request.requester.equals(requester.getUUID())) {
                    acceptor.sendMessage(new TextComponent("§cNão há pedido de TPA pendente deste jogador!"), acceptor.getUUID());
                    return 0;
                }
                
                TPAManager.removeRequest(acceptor.getUUID());
                
                requester.sendMessage(new TextComponent("§aTeleporte iniciando em 3 segundos..."), requester.getUUID());
                acceptor.sendMessage(new TextComponent("§aTeleporte aceito!"), acceptor.getUUID());
                
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        
                        MinecraftServer server = context.getSource().getServer();
                        server.execute(() -> {
                            if (TPAManager.isInCombat(requester.getUUID())) {
                                requester.sendMessage(new TextComponent("§cTeleporte cancelado - Você entrou em combate!"), requester.getUUID());
                                return;
                            }
                            
                            requester.teleportTo(acceptor.getX(), acceptor.getY(), acceptor.getZ());
                            requester.sendMessage(new TextComponent("§aTeleportado!"), requester.getUUID());
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                
                return 1;
            });
    }
}
