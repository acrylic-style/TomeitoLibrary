package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

/**
 * @deprecated use {@link xyz.acrylicstyle.tomeito_api.messaging.PluginChannelListener}
 */
@Deprecated
public class PluginMessageUtils {
    @Deprecated
    public static void getRank(Player player, Callback<Ranks> callback) {
        // message isn't used
        TomeitoAPI.getPluginChannelListener().get(Ranks.class, player, player.getUniqueId().toString(), "", "dtc:rank").then(rank -> {
            callback.done(rank, null);
            return null;
        }).queue();
    }

    @Deprecated
    public static void get(Player player, String message, String channel, Callback<String> callback) {
        TomeitoAPI.getPluginChannelListener().get(player, player.getUniqueId().toString(), message, channel, callback);
    }

    @Deprecated
    public static void get(Player player, String message, String subchannel, String s, Callback<String> callback) {
        TomeitoAPI.getPluginChannelListener().get(player, subchannel, message, s, callback);
    }
}

