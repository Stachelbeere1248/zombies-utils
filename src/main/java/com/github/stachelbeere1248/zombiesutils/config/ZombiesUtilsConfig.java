package com.github.stachelbeere1248.zombiesutils.config;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.utils.LanguageSupport;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ZombiesUtilsConfig {
    public static Configuration config;
    private static Property slaToggle;
    private static Property slaShortener;
    private static Property shortSpawntime;
    private static Property sst;
    private static Property chatMacro;
    private static Property defaultCategory;
    private static Property waveOffset;
    private static Property language;
    private static Property auditory;

    public static void load() {
        ZombiesUtils.getInstance().getLogger().debug("Loading config...");
        config.load();
        ZombiesUtils.getInstance().getLogger().debug("Config loaded.");

        language = config.get(
                Configuration.CATEGORY_GENERAL,
                "Language",
                "EN",
                "Your Hypixel language",
                LanguageSupport.getLanguages()
        );
        slaToggle = config.get(
                Configuration.CATEGORY_GENERAL,
                "SLA Launcher",
                false,
                "Should SLA be started when a game starts?"
        );
        slaShortener = config.get(
                Configuration.CATEGORY_GENERAL,
                "shortened SLA",
                true,
                "If on, inactive windows / rooms will not show"
        );
        chatMacro = config.get(
                Configuration.CATEGORY_GENERAL,
                "Chat Macro",
                "T",
                "The Text to be sent when pressing the chat-macro hotkey"
        );
        defaultCategory = config.get(
                Configuration.CATEGORY_GENERAL,
                "Default Category",
                "general",
                "name of the category to be selected unless specified using /runCategory"
        );
        waveOffset = config.get(
                Configuration.CATEGORY_GENERAL,
                "RL-mode offset",
                -28,
                "ticks to be added to the wave spawn time",
                -200,
                200
        );
        shortSpawntime = config.get(
                Configuration.CATEGORY_GENERAL,
                "passed wave spawns",
                true,
                "Display spawn-time for passed waves"
        );
        auditory = config.get(Configuration.CATEGORY_GENERAL,
                "auditory sst",
                new int[]{-40, -20, 0},
                "Tick-offset to play sound",
                -200,
                200,
                false,
                5
        );
        sst = config.get(
                Configuration.CATEGORY_GENERAL,
                "SST HUD",
                false,
                "Enable if not using SST by Sosean"

        );
    }

    public static short getWaveOffset() {
        return (short) waveOffset.getInt();
    }

    public static boolean isSlaToggled() {
        return slaToggle.getBoolean();
    }

    public static boolean isSlaShortened() {
        return slaShortener.getBoolean();
    }

    public static boolean isSpawntimeNotShortened() {
        return shortSpawntime.getBoolean();
    }

    public static String getChatMacro() {
        return chatMacro.getString();
    }

    public static String getDefaultCategory() {
        return defaultCategory.getString();
    }

    public static String getLanguage() {
        return language.getString();
    }

    public static int[] getAuditory() {
        return auditory.getIntList();
    }
    public static boolean getSST() {
        return sst.getBoolean();
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.@NotNull OnConfigChangedEvent event) {
        if (event.modID.equals("zombiesutils") && event.configID == null) {
            config.save();
            ZombiesUtilsConfig.load();
        }
    }
}
