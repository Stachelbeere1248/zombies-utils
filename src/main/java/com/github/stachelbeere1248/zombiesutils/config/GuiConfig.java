package com.github.stachelbeere1248.zombiesutils.config;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import net.minecraft.client.gui.GuiScreen;

public class GuiConfig extends net.minecraftforge.fml.client.config.GuiConfig {
    public GuiConfig(GuiScreen parentScreen) {
        super(
                parentScreen,
                ZombiesUtils.getInstance().getConfig().getRootElements(),
                "zombiesutils",
                false,
                false,
                "Zombies Utils"
        );
    }
}
