package com.github.stachelbeere1248.zombiesutils.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuickZombiesCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "qz";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/qz <de|bb|aa|p>";
    }

    @Override
    public void processCommand(ICommandSender sender, String @NotNull [] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException(
                "[Missing option] options: de, bb, aa, p");
        else switch (args[0]) {
            case "de":
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_zombies_dead_end");
                break;
            case "bb":
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_zombies_bad_blood");
                break;
            case "aa":
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_zombies_alien_arcadium");
                break;
            case "p":
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play arcade_zombies_prison");
                break;
            default:
                throw new WrongUsageException(
                        "[Invalid option] options: de, bb, aa, p", args[0]);

        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String @NotNull [] args, BlockPos blockPos) {
        if (args.length == 1) return Arrays.asList("de", "bb", "aa");
        else return Collections.emptyList();
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

}
