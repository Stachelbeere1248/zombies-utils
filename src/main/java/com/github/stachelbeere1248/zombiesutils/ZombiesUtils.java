package com.github.stachelbeere1248.zombiesutils;

import com.github.stachelbeere1248.zombiesutils.commands.CategoryCommand;
import com.github.stachelbeere1248.zombiesutils.handlers.TickHandler;
import com.github.stachelbeere1248.zombiesutils.render.TimeRenderer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "zombiesutils", useMetadata=true, clientSideOnly = true)
public class ZombiesUtils {
    private static ZombiesUtils instance;
    private Configuration config;
    private Logger logger;
    public ZombiesUtils() {
        instance = this;
        System.out.println("Initialised zombies-utils");
    }
    public static ZombiesUtils getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new TimeRenderer());
        MinecraftForge.EVENT_BUS.register(new TickHandler());
        ClientCommandHandler.instance.registerCommand(new CategoryCommand());
    }
    public Configuration getConfig() {
        return config;
    }
    public Logger getLogger() {
        return logger;
    }
}
