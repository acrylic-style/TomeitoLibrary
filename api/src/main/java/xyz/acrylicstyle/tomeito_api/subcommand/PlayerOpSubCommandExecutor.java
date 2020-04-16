package xyz.acrylicstyle.tomeito_api.subcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

public abstract class PlayerOpSubCommandExecutor implements SubCommandExecutor {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = TomeitoAPI.ensurePlayer(sender);
        if (player == null) return;
        if (!player.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
            return;
        }
        onCommand(player, args);
    }

    public abstract void onCommand(Player player, String[] args);
}
