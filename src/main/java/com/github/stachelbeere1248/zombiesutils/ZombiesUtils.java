package com.github.stachelbeere1248.zombiesutils;

import com.github.stachelbeere1248.zombiesutils.commands.CommandRegistry;
import com.github.stachelbeere1248.zombiesutils.config.Hotkeys;
import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.handlers.Handlers;
import com.github.stachelbeere1248.zombiesutils.timer.GameManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(modid = "zombiesutils", useMetadata = true, clientSideOnly = true, guiFactory = "com.github.stachelbeere1248.zombiesutils.config.GuiFactory")
public class ZombiesUtils {
    private static ZombiesUtils instance;
    private final Hotkeys hotkeys;
    private final GameManager gameManager;
    private ZombiesUtilsConfig config;
    private Handlers handlers;
    private Logger logger;

    public ZombiesUtils() {
        hotkeys = new Hotkeys();
        gameManager = new GameManager();

        instance = this;
    }

    public static ZombiesUtils getInstance() {
        return instance;
    }

    public static boolean isHypixel() {
        String ip = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        return (ip.equals("localhost") || ip.matches("(.+\\.)?(hypixel\\.net)(:25565)?"));
    }

    @Mod.EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        this.config = new ZombiesUtilsConfig(new Configuration(
                event.getSuggestedConfigurationFile())
        );
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        handlers = new Handlers();
        handlers.registerAll();
        CommandRegistry.registerAll();
        hotkeys.registerAll();
    }

    public Logger getLogger() {
        return logger;
    }

    public Hotkeys getHotkeys() {
        return hotkeys;
    }

    public Handlers getHandlers() {
        return handlers;
    }

    public ZombiesUtilsConfig getConfig() {
        return config;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
