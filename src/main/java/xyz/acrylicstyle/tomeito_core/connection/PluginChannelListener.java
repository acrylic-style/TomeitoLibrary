package xyz.acrylicstyle.tomeito_core.connection;

import org.bukkit.Bukkit;
import org.bukkit.plugin.messaging.PluginMessageListener;
import util.CollectionList;
import util.CollectionStrictSync;
import xyz.acrylicstyle.tomeito_core.TomeitoLib;
import xyz.acrylicstyle.tomeito_core.utils.Callback;

import java.io.*;

public class PluginChannelListener implements PluginMessageListener {
    private static CollectionStrictSync<String, Callback<String>> callbacks = new CollectionStrictSync<>();
    private static CollectionList<String> registeredListeners = new CollectionList<>();

    @Override
    public synchronized void onPluginMessageReceived(String tag, org.bukkit.entity.Player player, byte[] message) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String subchannel = in.readUTF();
            String input = in.readUTF(); // message
            callbacks.get(subchannel).done(input, null);
            callbacks.remove(subchannel);
        } catch (IOException e) {
            callbacks.get(player.getUniqueId().toString()).done(null, e);
            callbacks.remove(player.getUniqueId().toString());
        }
    }

    public synchronized void get(org.bukkit.entity.Player p, String subchannel, String message, String s, Callback<String> callback) {
        if (!registeredListeners.contains(s)) {
            Bukkit.getMessenger().registerIncomingPluginChannel(TomeitoLib.getPlugin(TomeitoLib.class), s, TomeitoLib.pcl);
            Bukkit.getMessenger().registerOutgoingPluginChannel(TomeitoLib.getPlugin(TomeitoLib.class), s);
            registeredListeners.add(s);
        }
        sendToBungeeCord(p, subchannel, message, s);
        callbacks.put(p.getUniqueId().toString(), callback);
    }

    private void sendToBungeeCord(org.bukkit.entity.Player p, String subchannel, String message, String s) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF(subchannel);
            out.writeUTF(message);
        } catch (IOException e) { // impossible?
            e.printStackTrace();
        }
        p.sendPluginMessage(TomeitoLib.getPlugin(TomeitoLib.class), s, b.toByteArray());
    }
}
