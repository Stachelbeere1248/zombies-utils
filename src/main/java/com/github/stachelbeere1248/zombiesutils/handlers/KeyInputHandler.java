package com.github.stachelbeere1248.zombiesutils.handlers;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.config.Hotkeys;
import com.github.stachelbeere1248.zombiesutils.config.ZombiesUtilsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;


public class KeyInputHandler {
    /*
    @SuppressWarnings("SpellCheckingInspection")
    private final String[] secret = new String[]{
            "Rule #1: Use Zombies Utils.",
            "Rule #2: Use the god damn maarkro.",
            "Rule #3: Be as toxic as possible (not).",
            "Rule #4: /friend remove 9c3584fe963d4d579081e7e9e225763d",
            "Rule #5: Play legit.",
            "Rule #6: Don't type wave 3.",
            "Rule #7: Don't trust shotgun.",
            "Rule #8: Hit your wallshots.",
            "Rule #9: Start the recording right now.",
            "Rule #10: Don't s1 on wr pace.",
            "Rule #11: There can be only one GOAT.",
            "Rule #12: Fast T please.",
            "Rule #13: Step back if insta.",
            "Rule #14: No killing Teammates.",
            "rule #15: No arguing because prplx is always left."
    };
    private int secretIndex = 0;*/
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState() && Minecraft.getMinecraft().currentScreen == null) {
            Hotkeys hotkeys = ZombiesUtils.getInstance().getHotkeys();
            if (Keyboard.getEventKey() == hotkeys.getChatMacro().getKeyCode()) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(
                        ZombiesUtilsConfig.getChatMacro()
                );
            } else if (Keyboard.getEventKey() == hotkeys.getDebugger().getKeyCode()) {
                for (Entity entity:Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    System.out.println(entity.getNBTTagCompound());
                }
            }
        }
    }
}
