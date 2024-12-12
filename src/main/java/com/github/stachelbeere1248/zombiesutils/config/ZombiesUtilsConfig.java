package com.github.stachelbeere1248.zombiesutils.config;

import com.github.stachelbeere1248.zombiesutils.utils.LanguageSupport;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ZombiesUtilsConfig {
    public final Configuration config;
    private Property sstHud;
    private Property offset;
    private Property slaToggle;
    private Property slaShortener;
    private Property shortSpawntime;
    private Property chatMacro;
    private Property defaultCategory;
    private Property language;
    private Property auditory;
    private Property cpsCounter;
    private Property announcePB;
    private Property playerVis;
    private Property playerVisRange;

    public ZombiesUtilsConfig(Configuration config) {
        this.config = config;
        this.read();
    }

    private void read() {
        config.load();

        //SST
        sstHud = config.get(
                "SST",
                "enabled",
                true,
                "Show the spawn-time HUD?"
        );
        auditory = config.get(
                "SST",
                "auditory",
                new int[]{-40, -20, 0},
                "For every entry a sound will be played x ticks before the wave spawn.",
                -200,
                200,
                false,
                5
        );
        //noinspection SpellCheckingInspection
        shortSpawntime = config.get(
                "SST",
                "autohide",
                false,
                "Hide passed rounds?"
        );
        offset = config.get(
                "SST",
                "offset",
                -28,
                "Offset is added while in RL-mode",
                -200,
                200
        );

        //SLA
        slaToggle = config.get(
                "SLA",
                "autostart",
                false,
                "Should SLA be started when a game starts?"
        );
        slaShortener = config.get(
                "SLA",
                "shortened SLA",
                true,
                "If on, inactive windows / rooms will not show"
        );

        //Timer
        defaultCategory = config.get(
                "timer",
                "Default Category",
                "general",
                "name of the category to be selected unless specified using /runCategory"
        );
        announcePB = config.get(
                "timer",
                "announce",
                true,
                "Whether to announce PBs."
        );

        //Player Visibility
        playerVis = config.get(
                "PlayerVis",
                "default",
                false,
                "If players should always be visible"
        );
        playerVisRange = config.get(
                "PlayerVis",
                "range",
                4,
                "The range within which players are hidden",
                0,
                50
        );


        //ROOT
        language = config.get(
                Configuration.CATEGORY_GENERAL,
                "Language",
                "EN",
                "Your Hypixel language",
                LanguageSupport.getLanguages()
        );
        chatMacro = config.get(
                Configuration.CATEGORY_GENERAL,
                "Chat Macro",
                "T",
                "The Text to be sent when pressing the chat-macro hotkey"
        );
        cpsCounter = config.get(
                Configuration.CATEGORY_GENERAL,
                "cps",
                false,
                "whether to show cps"
        );
    }

    private List<IConfigElement> getSpawntimeElements() {
        return Arrays.asList(
                new CustomConfigElement("Enabled", sstHud),
                new CustomConfigElement("Auditory", auditory),
                new CustomConfigElement("RL pre-timing", offset),
                new CustomConfigElement("Truncate", shortSpawntime)
        );
    }

    private List<IConfigElement> getSlaElements() {
        return Arrays.asList(
                new CustomConfigElement("Enabled", slaToggle),
                new CustomConfigElement("Truncate", slaShortener)
        );
    }

    private List<IConfigElement> getTimerElements() {
        return Arrays.asList(
                new CustomConfigElement("Default category", defaultCategory),
                new CustomConfigElement("PB announcements", announcePB)
        );
    }

    private List<IConfigElement> getPlayerVisElements() {
        return Arrays.asList(
                new CustomConfigElement("Enabled", playerVis),
                new CustomConfigElement("Range", playerVisRange)
        );
    }

    List<IConfigElement> getRootElements() {
        return Arrays.asList(
                new CustomConfigElement("Language", language),
                new DummyConfigElement.DummyCategoryElement("Timer", "", getTimerElements()),
                new DummyConfigElement.DummyCategoryElement("SST", "", getSpawntimeElements()),
                new DummyConfigElement.DummyCategoryElement("SLA", "", getSlaElements()),
                new DummyConfigElement.DummyCategoryElement("Player Visibility", "", getPlayerVisElements()),
                new CustomConfigElement("Macro message", chatMacro),
                new CustomConfigElement("CPS counter", cpsCounter)

        );
    }


    public short getOffset() {
        return (short) offset.getInt();
    }

    public boolean isSlaToggled() {
        return slaToggle.getBoolean();
    }

    public boolean isSlaShortened() {
        return slaShortener.getBoolean();
    }

    public boolean isSpawntimeShortened() {
        return shortSpawntime.getBoolean();
    }

    public String getChatMacro() {
        return chatMacro.getString();
    }

    public String getDefaultCategory() {
        return defaultCategory.getString();
    }

    public String getLanguage() {
        return language.getString();
    }

    public int[] getAuditory() {
        return auditory.getIntList();
    }

    public boolean getSST() {
        return sstHud.getBoolean();
    }

    public boolean getCpsToggle() {
        return cpsCounter.getBoolean();
    }

    public boolean getAnnouncePB() {
        return announcePB.getBoolean();
    }

    public boolean getPlayerVis() {
        return playerVis.getBoolean();
    }

    public int getPlayerVisRange() {
        return playerVisRange.getInt();
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.@NotNull OnConfigChangedEvent event) {
        if (event.modID.equals("zombiesutils") && event.configID == null) {
            config.save();
        }
    }

}
