package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import net.minecraftforge.common.MinecraftForge;

public class Handlers {
    private final RenderGameOverlayHandler renderer;
    private final RenderPlayerHandler renderPlayerHandler;

    public Handlers() {
        renderer = new RenderGameOverlayHandler();
        renderPlayerHandler = new RenderPlayerHandler();
    }

    public void registerAll() {
        MinecraftForge.EVENT_BUS.register(ZombiesUtils.getInstance().getConfig());
        MinecraftForge.EVENT_BUS.register(renderer);
        MinecraftForge.EVENT_BUS.register(renderPlayerHandler);
        MinecraftForge.EVENT_BUS.register(new TickHandler());
        MinecraftForge.EVENT_BUS.register(new ChatHandler());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }

    public RenderGameOverlayHandler getRenderer() {
        return renderer;
    }

    public RenderPlayerHandler getRenderPlayerHandler() {
        return renderPlayerHandler;
    }
}
