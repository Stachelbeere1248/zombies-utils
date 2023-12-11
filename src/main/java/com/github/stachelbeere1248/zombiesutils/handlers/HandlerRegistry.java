package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import net.minecraftforge.common.MinecraftForge;

public class HandlerRegistry {
    public static void registerAll() {
        MinecraftForge.EVENT_BUS.register(new ZombiesUtilsConfig());
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayHandler());
        MinecraftForge.EVENT_BUS.register(new TickHandler());
        MinecraftForge.EVENT_BUS.register(new ChatHandler());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }
}
