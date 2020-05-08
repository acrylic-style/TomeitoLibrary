package xyz.acrylicstyle.tomeito_api.messaging;

import org.bukkit.Bukkit;
import org.bukkit.plugin.messaging.PluginMessageListener;
import util.CollectionList;
import util.CollectionStrictSync;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.utils.Callback;

import java.io.*;

public class PluginChannelListener implements PluginMessageListener {
    public static final PluginChannelListener pcl = new PluginChannelListener();
    private final CollectionStrictSync<String, CollectionStrictSync<String, Callback<String>>> callbacks = new CollectionStrictSync<>();
    private final CollectionList<String> registeredListeners = new CollectionList<>();

    @Override
    public synchronized void onPluginMessageReceived(String tag, org.bukkit.entity.Player player, byte[] message) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String subchannel = in.readUTF();
            String input = in.readUTF(); // message
            /*
            Log.debug("Received plugin message!");
            Log.debug("Tag: " + tag);
            Log.debug("Subchannel: " + subchannel);
            Log.debug("Input: " + input);
            */
            CollectionStrictSync<String, Callback<String>> callbacks2 = callbacks.get(tag);
            callbacks2.get(subchannel).done(input, null);
            callbacks2.remove(subchannel);
            callbacks.put(tag, callbacks2);
        } catch (IOException e) {
            e.printStackTrace();
            CollectionStrictSync<String, Callback<String>> callbacks2 = callbacks.get(tag);
            callbacks2.remove(player.getUniqueId().toString());
            callbacks.put(tag, callbacks2);
        }
    }

    public synchronized void get(org.bukkit.entity.Player p, String subchannel, String message, String s, Callback<String> callback) {
        if (!registeredListeners.contains(s)) {
            Bukkit.getMessenger().registerIncomingPluginChannel(TomeitoAPI.getInstance(), s, TomeitoAPI.getPluginChannelListener());
            Bukkit.getMessenger().registerOutgoingPluginChannel(TomeitoAPI.getInstance(), s);
            registeredListeners.add(s);
        }
        if (!callbacks.containsKey(s)) {
            callbacks.put(s, new CollectionStrictSync<>());
        }
        CollectionStrictSync<String, Callback<String>> callbacks2 = callbacks.get(s);
        callbacks2.put(subchannel, callback);
        callbacks.put(s, callbacks2);
        sendToBungeeCord(p, subchannel, message, s);
    }

    private void sendToBungeeCord(org.bukkit.entity.Player p, String subchannel, String message, String s) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF(subchannel);
            out.writeUTF(message);
        } catch (IOException e) { throw new RuntimeException(e); }
        p.sendPluginMessage(TomeitoAPI.getInstance(), s, b.toByteArray());
    }
}
