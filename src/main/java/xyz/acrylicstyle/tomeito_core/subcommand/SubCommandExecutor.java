package xyz.acrylicstyle.tomeito_core.subcommand;

import org.bukkit.command.CommandSender;

public interface SubCommandExecutor {
    void onCommand(CommandSender sender, String[] args);
}
