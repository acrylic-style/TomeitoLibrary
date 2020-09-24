package xyz.acrylicstyle.tomeito_api.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

public abstract class PlayerSubCommandExecutor implements SubCommandExecutor {
    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = TomeitoAPI.ensurePlayer(sender);
        if (player == null) return;
        onCommand(player, args);
    }

    public abstract void onCommand(@NotNull Player player, @NotNull String[] args);
}
