package xyz.acrylicstyle.tomeito_api.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class OpCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
            return true;
        }
        onCommand(sender, args);
        return true;
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
