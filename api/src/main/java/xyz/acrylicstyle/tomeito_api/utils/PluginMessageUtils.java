package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

@SuppressWarnings("unused")
public class PluginMessageUtils {
    public static void getRank(Player player, Callback<Ranks> callback) {
        // message isn't used
        TomeitoAPI.getPluginChannelListener().get(player, player.getUniqueId().toString(), "", "dtc:rank", (rank, e) -> {
            if (rank == null && e != null) { // it shouldn't happen... if happened, check the proxy log
                e.printStackTrace();
                throw new NullPointerException("Couldn't fetch rank for player: " + player.getUniqueId());
            }
            callback.done(Ranks.valueOf(rank), null);
        });
    }

    public static void get(Player player, String message, String s, Callback<String> callback) {
        // message isn't used
        TomeitoAPI.getPluginChannelListener().get(player, player.getUniqueId().toString(), message, s, (s1, e) -> {
            if (s1 == null && e != null) { // it shouldn't happen... if happened, check the proxy log
                NullPointerException npe = new NullPointerException();
                npe.initCause(e);
                throw npe;
            }
            callback.done(s1, null);
        });
    }

    public static void get(Player player, String message, String subchannel, String s, Callback<String> callback) {
        // message isn't used
        TomeitoAPI.getPluginChannelListener().get(player, subchannel, message, s, (s1, e) -> {
            if (s1 == null && e != null) { // it shouldn't happen... if happened, check the proxy log
                NullPointerException npe = new NullPointerException();
                npe.initCause(e);
                throw npe;
            }
            callback.done(s1, null);
        });
    }
}

