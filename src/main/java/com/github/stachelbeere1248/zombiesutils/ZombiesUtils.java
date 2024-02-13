package com.github.stachelbeere1248.zombiesutils;

import com.github.stachelbeere1248.zombiesutils.commands.CommandRegistry;
import com.github.stachelbeere1248.zombiesutils.config.Hotkeys;
import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import com.github.stachelbeere1248.zombiesutils.handlers.HandlerRegistry;
import net.minecraft.client.Minecraft;
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
    private final Hotkeys hotkeys;
    private Logger logger;

    public ZombiesUtils() {
        hotkeys = new Hotkeys();
        instance = this;
    }

    public static ZombiesUtils getInstance() {
        return instance;
    }

    @Mod.EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ZombiesUtilsConfig.config = new Configuration(
                event.getSuggestedConfigurationFile(),
                "1.2.4"
        );
        ZombiesUtilsConfig.load();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        HandlerRegistry.registerAll();
        CommandRegistry.registerAll();
        hotkeys.registerAll();
    }

    public Logger getLogger() {
        return logger;
    }

    public Hotkeys getHotkeys() {
        return hotkeys;
    }
    public static boolean isHypixel() {
        return Minecraft.getMinecraft().getCurrentServerData().serverIP.matches("(.+\\.)?(hypixel\\.net)(:25565)?");
    }
}
