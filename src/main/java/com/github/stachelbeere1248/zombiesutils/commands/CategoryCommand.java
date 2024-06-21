package com.github.stachelbeere1248.zombiesutils.commands;

import com.github.stachelbeere1248.zombiesutils.timer.Timer;
import com.github.stachelbeere1248.zombiesutils.timer.recorder.Category;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CategoryCommand implements ICommand {
    public CategoryCommand() {

    }

    @Override
    public String getCommandName() {
        return "category";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/category <category-name>";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("runCategory", "cat");
    }

    @Override
    public void processCommand(ICommandSender sender, String @NotNull [] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException("Please enter a name for the category");
        else {
            String cat = args[0];
            if (cat.contains(File.separator))
                throw new WrongUsageException("Your name must not contain '" + File.separator + "' as this is the systems separator character for folder" + File.separator + "sub-folder");
            Category.setSelectedCategory(cat);
            sender.addChatMessage(new ChatComponentText("§eSet category to §c" + cat));
            Timer.getInstance().ifPresent(timer -> timer.setCategory(new Category()));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos blockPos) {
        return Arrays.asList(Category.getCategories());
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int compareTo(@NotNull ICommand command) {
        return this.getCommandName().compareTo(command.getCommandName());
    }
}
