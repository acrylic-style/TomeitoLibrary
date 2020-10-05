package xyz.acrylicstyle.tomeito_api.messaging;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import util.CollectionList;
import util.CollectionStrictSync;
import util.promise.Promise;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.utils.Callback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

    /**
     * @deprecated Use {@link #get(Player, String, String, String)} instead
     */
    @Deprecated
    public synchronized void get(org.bukkit.entity.Player p, String subchannel, String message, String channel, Callback<String> callback) {
        if (!registeredListeners.contains(channel)) {
            Bukkit.getMessenger().registerIncomingPluginChannel(TomeitoAPI.getInstance(), channel, TomeitoAPI.getPluginChannelListener());
            Bukkit.getMessenger().registerOutgoingPluginChannel(TomeitoAPI.getInstance(), channel);
            registeredListeners.add(channel);
        }
        if (!callbacks.containsKey(channel)) {
            callbacks.put(channel, new CollectionStrictSync<>());
        }
        CollectionStrictSync<String, Callback<String>> callbacks2 = callbacks.get(channel);
        callbacks2.put(subchannel, callback);
        callbacks.put(channel, callbacks2);
        sendToBungeeCord(p, subchannel, message, channel);
    }

    /**
     * Sends plugin message and waits for up to 10 seconds.
     * @param p the player (sender)
     * @param subchannel the subchannel
     * @param message the message
     * @param channel the channel, also known as "tag"
     * @return the promise
     */
    public synchronized Promise<String> get(Player p, String subchannel, String message, String channel) {
        return new Promise<String>() {
            @Override
            public String apply(Object o) {
                PluginChannelListener.this.get(p, subchannel, message, channel, (s, throwable2) -> {
                    if (throwable2 != null) {
                        reject(throwable2);
                        return;
                    }
                    resolve(s);
                });
                return waitUntilResolve(10000);
            }
        };
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
