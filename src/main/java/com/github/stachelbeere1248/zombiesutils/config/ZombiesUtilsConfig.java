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

    private static Property chatMacro;
    private static Property defaultCategory;
    private static Property waveOffset;
    private static Property language;

    public static void load() {
        ZombiesUtils.getInstance().getLogger().debug("Loading config...");
        config.load();
        ZombiesUtils.getInstance().getLogger().debug("Config loaded.");

        language = config.get(
                Configuration.CATEGORY_GENERAL,
                "Language",
                "EN",
                "Your Hypixel language",
                LanguageSupport.Languages.list()
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

    public static LanguageSupport.Languages getLanguage() {
        return LanguageSupport.Languages.valueOf(language.getString());
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.@NotNull OnConfigChangedEvent event) {
        if (event.modID.equals("zombiesutils") && event.configID == null) {
            config.save();
            ZombiesUtilsConfig.load();
        }
    }
}
