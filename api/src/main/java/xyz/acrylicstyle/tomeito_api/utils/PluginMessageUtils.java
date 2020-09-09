package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

public class PluginMessageUtils {
    @Deprecated
    public static void getRank(Player player, Callback<Ranks> callback) {
        // message isn't used
        TomeitoAPI.getPluginChannelListener().get(player, player.getUniqueId().toString(), "", "dtc:rank", (rank, e) -> {
            if (rank == null && e != null) { // it shouldn't happen... if happened, check the proxy log
                e.printStackTrace();
                throw new RuntimeException("Couldn't fetch rank for player: " + player.getUniqueId());
            }
            callback.done(Ranks.valueOf(rank), null);
        });
    }

    public static void get(Player player, String message, String channel, Callback<String> callback) {
        TomeitoAPI.getPluginChannelListener().get(player, player.getUniqueId().toString(), message, channel, (s1, e) -> {
            if (s1 == null && e != null) {
                throw new RuntimeException(e);
            }
            callback.done(s1, null);
        });
    }

    public static void get(Player player, String message, String subchannel, String s, Callback<String> callback) {
        TomeitoAPI.getPluginChannelListener().get(player, subchannel, message, s, (s1, e) -> {
            if (s1 == null && e != null) {
                throw new RuntimeException(e);
            }
            callback.done(s1, null);
        });
    }
}

