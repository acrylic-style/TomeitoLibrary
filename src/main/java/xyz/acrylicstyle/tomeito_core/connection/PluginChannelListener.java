package xyz.acrylicstyle.tomeito_core.connection;

import org.bukkit.Bukkit;
import org.bukkit.plugin.messaging.PluginMessageListener;
import util.CollectionList;
import util.CollectionStrictSync;
import xyz.acrylicstyle.tomeito_core.TomeitoLib;
import xyz.acrylicstyle.tomeito_core.utils.Callback;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.io.*;

public class PluginChannelListener implements PluginMessageListener {
    private static CollectionStrictSync<String, CollectionStrictSync<String, Callback<String>>> callbacks = new CollectionStrictSync<>();
    private static CollectionList<String> registeredListeners = new CollectionList<>();
    @SuppressWarnings("unused")
    private String lastTag = null; // for debug

    @Override
    public synchronized void onPluginMessageReceived(String tag, org.bukkit.entity.Player player, byte[] message) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String subchannel = in.readUTF();
            String input = in.readUTF(); // message
            Log.debug("Received plugin message!");
            Log.debug("Tag: " + tag);
            Log.debug("Subchannel: " + subchannel);
            Log.debug("Input: " + input);
            CollectionStrictSync<String, Callback<String>> callbacks2 = callbacks.get(tag);
            lastTag = tag;
            callbacks2.get(subchannel).done(input, null);
            callbacks2.remove(subchannel);
            callbacks.put(tag, callbacks2);
        } catch (IOException e) {
            Log.error("Error when received plugin message");
            e.printStackTrace();
            lastTag = tag;
            CollectionStrictSync<String, Callback<String>> callbacks2 = callbacks.get(tag);
            callbacks2.remove(player.getUniqueId().toString());
            callbacks.put(tag, callbacks2);
        }
    }

    public synchronized void get(org.bukkit.entity.Player p, String subchannel, String message, String s, Callback<String> callback) {
        if (!registeredListeners.contains(s)) {
            Bukkit.getMessenger().registerIncomingPluginChannel(TomeitoLib.getPlugin(TomeitoLib.class), s, TomeitoLib.pcl);
            Bukkit.getMessenger().registerOutgoingPluginChannel(TomeitoLib.getPlugin(TomeitoLib.class), s);
            registeredListeners.add(s);
        }
        if (!callbacks.containsKey(s)) {
            callbacks.put(s, new CollectionStrictSync<>());
        }
        CollectionStrictSync<String, Callback<String>> callbacks2 = callbacks.get(s);
        callbacks2.put(p.getUniqueId().toString(), callback);
        callbacks.put(s, callbacks2);
        sendToBungeeCord(p, subchannel, message, s);
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
