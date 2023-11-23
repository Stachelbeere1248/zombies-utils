package com.github.stachelbeere1248.zombiesutils.config;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ZombiesUtilsConfig {
    public static Configuration config;
    private static boolean slaToggle;
    private static boolean slaShortener;
    private static String chatMacro;
    private static String defaultCategory;
    private static short waveOffset;
    public static void load() {
        ZombiesUtils.getInstance().getLogger().debug("Loading config...");
        config.load();
        ZombiesUtils.getInstance().getLogger().debug("Config loaded.");

        slaToggle = config.getBoolean(
                "SLA Launcher",
                Configuration.CATEGORY_GENERAL,
                slaToggle,
                "Should SLA be started when a game starts?"
        );
        slaShortener = config.getBoolean(
                "shortened SLA",
                Configuration.CATEGORY_GENERAL,
                slaShortener,
                "If on, inactive windows / rooms will not show"
        );
        chatMacro = config.getString(
                "Chat Macro",
                Configuration.CATEGORY_GENERAL,
                "T",
                "The Text to be sent when pressing the chat-macro hotkey"
        );
        defaultCategory = config.getString(
                "Default Category",
                Configuration.CATEGORY_GENERAL,
                "general",
                "name of the category to be selected unless specified using /runCategory"
        );
        waveOffset = (short) config.getInt(
                "spawn-time offset ticks",
                Configuration.CATEGORY_GENERAL,
                0,
                -200,
                200,
                "max: 200 ticks"
        );

        ZombiesUtils.getInstance().getLogger().debug("Saving Config...");
        config.save();
        ZombiesUtils.getInstance().getLogger().debug("Config saved.");
    }

    public static short getWaveOffset() {
        return waveOffset;
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.@NotNull OnConfigChangedEvent event) {
        if (event.modID.equals("zombiesutils") && event.configID == null) {
            config.save();
            ZombiesUtilsConfig.load();
        }
    }
    public static boolean isSlaToggled() {
        return slaToggle;
    }
    public static boolean isSlaShortened() {
        return slaShortener;
    }
    public static String getChatMacro() {
        return chatMacro;
    }
    public static String getDefaultCategory() {
        return defaultCategory;
    }
}
