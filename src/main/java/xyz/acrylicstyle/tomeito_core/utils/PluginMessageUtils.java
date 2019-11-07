package xyz.acrylicstyle.tomeito_core.utils;

import org.bukkit.entity.Player;
import xyz.acrylicstyle.tomeito_core.TomeitoLib;

public class PluginMessageUtils {
    public static void getRank(Player player, Callback<Ranks> callback) {
        TomeitoLib.pcl.get(player, player.getUniqueId().toString(), "", "dtc:rank", new Callback<String>() { // message isn't used
            @Override
            public void done(String rank, Throwable e) {
                if (rank == null && e != null) { // it shouldn't happen... if happened, check the proxy log
                    e.printStackTrace();
                    throw new NullPointerException("Couldn't fetch rank for player: " + player.getUniqueId());
                }
                callback.done(Ranks.valueOf(rank), null);
            }
        });
    }

    public static void get(Player player, String message, String s, Callback<String> callback) {
        TomeitoLib.pcl.get(player, player.getUniqueId().toString(), message, s, new Callback<String>() { // message isn't used
            @Override
            public void done(String s, Throwable e) {
                if (s == null && e != null) { // it shouldn't happen... if happened, check the proxy log
                    NullPointerException npe = new NullPointerException();
                    npe.initCause(e);
                    throw npe;
                }
                callback.done(s, null);
            }
        });
    }

    public static void get(Player player, String message, String subchannel, String s, Callback<String> callback) {
        TomeitoLib.pcl.get(player, subchannel, message, s, new Callback<String>() { // message isn't used
            @Override
            public void done(String s, Throwable e) {
                if (s == null && e != null) { // it shouldn't happen... if happened, check the proxy log
                    NullPointerException npe = new NullPointerException();
                    npe.initCause(e);
                    throw npe;
                }
                callback.done(s, null);
            }
        });
    }
}

