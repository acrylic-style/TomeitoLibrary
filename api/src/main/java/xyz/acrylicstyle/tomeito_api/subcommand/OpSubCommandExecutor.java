package xyz.acrylicstyle.tomeito_api.subcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class OpSubCommandExecutor implements SubCommandExecutor {
    public void onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        onOpCommand(sender, args);
    }

    public abstract void onOpCommand(@NotNull CommandSender sender, @NotNull String[] args);
}
