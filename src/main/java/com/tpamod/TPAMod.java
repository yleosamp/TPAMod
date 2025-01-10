package com.tpamod;

import com.tpamod.commands.CommandRegistration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("tpamod")
public class TPAMod {
    public static final String MOD_ID = "tpamod";
    
    public TPAMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
