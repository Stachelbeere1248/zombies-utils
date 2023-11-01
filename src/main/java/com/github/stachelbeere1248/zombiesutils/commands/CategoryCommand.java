package com.github.stachelbeere1248.zombiesutils.commands;

import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.Arrays;
import java.util.List;

public class CategoryCommand extends CommandBase {
    public CategoryCommand () {

    }
    @Override
    public String getCommandName() {
        return "runCategory";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "runCategory <category-name>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            IChatComponent error = new ChatComponentText("Please input the name for the category");
            sender.addChatMessage(error);
        } else {
            Category.setSelectedCategory(args[0]);
            Timer.getInstance().ifPresent(timer -> timer.setCategory(new Category()));
        }
    }
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos blockPos) {
        return Arrays.asList(Category.getCategories());
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
