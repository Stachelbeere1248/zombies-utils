package com.github.stachelbeere1248.zombiesutils.commands;

import com.github.stachelbeere1248.zombiesutils.ZombiesUtils;
import com.github.stachelbeere1248.zombiesutils.utils.InvalidMapException;
import com.github.stachelbeere1248.zombiesutils.utils.ScoardboardException;
import com.github.stachelbeere1248.zombiesutils.utils.Scoreboard;
import net.minecraft.command.*;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZombiesUtilsCommand extends CommandBase {
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
                    case "kill":
                        String serverNumber = Scoreboard.getServerNumber().orElse("");
                        if (args.length == 3) serverNumber = args[2];
                        ZombiesUtils.getInstance().getGameManager().endGame(serverNumber, false);
                        break;
                    case "killall":
                        ZombiesUtils.getInstance().getGameManager().killAll();
                    case "split":
                        try {
                            ZombiesUtils.getInstance().getGameManager().splitOrNew(Integer.parseInt(args[2]));
                        } catch (NumberFormatException | NullPointerException ignored) {
                            throw new NumberInvalidException("t", args[2]);
                        } catch (ScoardboardException | InvalidMapException e) {
                            ZombiesUtils.getInstance().getLogger().error(e.getStackTrace());
                        }
                        break;
                    default:
                        throw new WrongUsageException(
                                "[Invalid option] options: kill, killall, split", args[0]);
                }
                break;
            default:
                throw new WrongUsageException(
                        "[Invalid option] options: timer", args[0]);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String @NotNull [] args, BlockPos blockPos) {
        if (args.length == 1) return new ArrayList<>(Collections.singleton("timer"));
        else if (args.length == 2) {
            switch (args[0]) {
                case "timer":
                    return new ArrayList<>(Arrays.asList("kill", "killall", "split"));
                default:
                    return Collections.emptyList();
            }
        } else if (args.length == 3) {
            switch (args[0]) {
                case "timer":
                    switch (args[1]) {
                        case "kill":
                            return new ArrayList<>(ZombiesUtils.getInstance().getGameManager().getGames());
                    }
                default:
                    return Collections.emptyList();
            }
        } else return Collections.emptyList();
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
