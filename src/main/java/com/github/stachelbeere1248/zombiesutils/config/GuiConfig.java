package com.github.stachelbeere1248.zombiesutils.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class GuiConfig extends net.minecraftforge.fml.client.config.GuiConfig {
    public GuiConfig(GuiScreen parentScreen) {
        super(
                parentScreen,
                new ConfigElement(ZombiesUtilsConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "zombiesutils",
                false,
                false,
                "Zombies Utils"
        );
    }
}
