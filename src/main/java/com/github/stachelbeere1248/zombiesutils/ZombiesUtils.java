package com.github.stachelbeere1248.zombiesutils;

import com.github.stachelbeere1248.zombiesutils.commands.CategoryCommand;
import com.github.stachelbeere1248.zombiesutils.commands.SlaCommand;
import com.github.stachelbeere1248.zombiesutils.config.Config;
import com.github.stachelbeere1248.zombiesutils.handlers.ChatHandler;
import com.github.stachelbeere1248.zombiesutils.handlers.TickHandler;
import com.github.stachelbeere1248.zombiesutils.render.RenderGameOverlayHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(modid = "zombiesutils", useMetadata = true, clientSideOnly = true, guiFactory = "com.github.stachelbeere1248.zombiesutils.config.GuiFactory")
public class ZombiesUtils {
    private static ZombiesUtils instance;
    private Logger logger;
    public ZombiesUtils() {
        instance = this;
    }
    public static ZombiesUtils getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Config.config = new Configuration(event.getSuggestedConfigurationFile());
        Config.load();
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderGameOverlayHandler());
        MinecraftForge.EVENT_BUS.register(new TickHandler());
        MinecraftForge.EVENT_BUS.register(new ChatHandler());
        MinecraftForge.EVENT_BUS.register(new Config());
        ClientCommandHandler.instance.registerCommand(new CategoryCommand());
        ClientCommandHandler.instance.registerCommand(new SlaCommand());
    }
    public Logger getLogger() {
        return logger;
    }
}
