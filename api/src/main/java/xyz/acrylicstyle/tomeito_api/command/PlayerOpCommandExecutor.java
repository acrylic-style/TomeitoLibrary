package xyz.acrylicstyle.tomeito_api.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

public abstract class PlayerOpCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = TomeitoAPI.ensurePlayer(sender);
        if (player == null) return true;
        if (!player.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
            return true;
        }
        onCommand(player, args);
        return true;
    }

    public abstract void onCommand(@NotNull Player player, @NotNull String[] args);
}
