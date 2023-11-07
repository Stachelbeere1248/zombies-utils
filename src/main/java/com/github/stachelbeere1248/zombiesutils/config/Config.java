package com.github.stachelbeere1248.zombiesutils.config;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class Config {
    public static Configuration config;

    private static boolean slaToggle;
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

        ZombiesUtils.getInstance().getLogger().debug("Saving Config...");
        config.save();
        ZombiesUtils.getInstance().getLogger().debug("Config saved.");
    }
    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.@NotNull OnConfigChangedEvent event) {
        if (event.modID.equals("zombiesutils") && event.configID == null) {
            config.save();
            Config.load();
        }
    }
    public static boolean isSlaToggled() {
        return slaToggle;
    }
}
