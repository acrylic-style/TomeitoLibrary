package xyz.acrylicstyle.tomeito_core.subcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class OpSubCommandExecutor implements SubCommandExecutor {
    public void onCommand(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        onOpCommand(sender, args);
    }

    public abstract void onOpCommand(CommandSender sender, String[] args);
}
