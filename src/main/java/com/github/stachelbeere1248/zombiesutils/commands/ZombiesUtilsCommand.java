package com.github.stachelbeere1248.zombiesutils.commands;

import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.command.*;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZombiesUtilsCommand  extends CommandBase {
    @Override
    public String getCommandName() {
        return "zombiesutils";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/zombiesutils";
    }

    @Override
    public void processCommand(ICommandSender sender, String @NotNull [] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException(
                "[Missing option] options: timer");
        else switch (args[0]) {
            case "timer":
                switch (args[1]) {
                    case "kill": Timer.dropInstances(); break;
                    case "split":
                        try {
                        Timer.getInstance().ifPresent(timer -> timer.split(Byte.parseByte(args[2])));
                        } catch (NumberFormatException | NullPointerException ignored) {
                            throw new NumberInvalidException("t",args[2]);
                        } break;
                    case "start": new Timer(
                                Scoreboard.getServerNumber().orElseThrow(() -> new RuntimeException("cannot figure out servernumber")),
                                Scoreboard.getMap().orElseThrow(() -> new RuntimeException("cannot figure out map")).map
                        ); break;
                    default:
                        throw new WrongUsageException(
                                "[Invalid option] options: kill, split, start", args[0]);
                } break;
            default:
                throw new WrongUsageException(
                        "[Invalid option] options: timer", args[0]);
        }
    }
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String @NotNull [] args, BlockPos blockPos) {
        if (args.length == 1) return new ArrayList<>(Collections.singleton("timer"));
        switch (args[0]) {
            case "timer":
                return new ArrayList<>(Arrays.asList("kill","split","start"));
            default: return Collections.emptyList();
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
