package xyz.acrylicstyle.tomeito_core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_core.TomeitoLib;

public abstract class PlayerOpCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = TomeitoLib.ensurePlayer(sender);
        if (player == null) return true;
        if (!player.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
            return true;
        }
        onCommand(player, args);
        return true;
    }

    public abstract void onCommand(Player player, String[] args);
}
