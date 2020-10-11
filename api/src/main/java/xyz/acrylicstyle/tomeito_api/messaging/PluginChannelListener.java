package xyz.acrylicstyle.tomeito_api.messaging;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.CollectionList;
import util.CollectionStrictSync;
import util.SneakyThrow;
import util.Validate;
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
            if (callbacks2 != null && callbacks2.containsKey(subchannel)) callbacks2.remove(subchannel).done(input, null);
            callbacks.put(tag, callbacks2);
        } catch (IOException e) {
            SneakyThrow.sneaky(e); // should not happen
        }
    }

    /**
     * @deprecated Use {@link #get(Player, String, String, String, int)} instead
     */
    @Deprecated
    public synchronized void get(org.bukkit.entity.Player p, String subchannel, String message, String channel, Callback<String> callback) {
        if (!registeredListeners.contains(channel)) {
            Bukkit.getMessenger().registerIncomingPluginChannel(TomeitoAPI.getInstance(), channel, this);
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
     * @param tag the tag, also known as "channel"
     * @return the promise
     */
    public synchronized Promise<String> get(Player p, String subchannel, String message, String tag) {
        return get(p, tag, subchannel, message, 10000);
    }

    /**
     * Sends plugin message and waits for the timeout.
     * @param player the player (sender)
     * @param subchannel the subchannel, if null, the player's uuid will be used.
     * @param message the message
     * @param tag the tag, also known as "channel"
     * @param timeout timeout in milliseconds
     * @return the promise
     */
    public synchronized Promise<String> get(@NotNull Player player, @NotNull String tag, @Nullable String subchannel, @Nullable String message, int timeout) {
        Validate.notNull(player, "player cannot be null");
        Validate.notNull(tag, "tag cannot be null");
        return new Promise<String>() {
            @Override
            public String apply(Object o) {
                PluginChannelListener.this.get(player, subchannel == null ? player.getUniqueId().toString() : subchannel, message == null ? "" : message, tag, (s, throwable2) -> {
                    if (throwable2 != null) {
                        reject(throwable2);
                        return;
                    }
                    resolve(s);
                });
                return waitUntilResolve(timeout);
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
