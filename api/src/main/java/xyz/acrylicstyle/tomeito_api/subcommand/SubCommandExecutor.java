package xyz.acrylicstyle.tomeito_api.subcommand;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface SubCommandExecutor {
    void onCommand(@NotNull CommandSender sender, @NotNull String[] args);
}
