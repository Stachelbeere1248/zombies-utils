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
    private static boolean shortSpawntime;

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
                false,
                "Should SLA be started when a game starts?"
        );
        slaShortener = config.getBoolean(
                "shortened SLA",
                Configuration.CATEGORY_GENERAL,
                true,
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
                "RL-mode offset",
                Configuration.CATEGORY_GENERAL,
                -28,
                -200,
                200,
                "max: 200 ticks"
        );
        shortSpawntime = config.getBoolean(
                "passed wave spawns",
                Configuration.CATEGORY_GENERAL,
                true,
                "Display spawn-time for passed waves"
        );
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
    public static boolean isSpawntimeNotShortened() {
        return shortSpawntime;
    }
    public static String getChatMacro() {
        return chatMacro;
    }
    public static String getDefaultCategory() {
        return defaultCategory;
    }
}
