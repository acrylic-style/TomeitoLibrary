package xyz.acrylicstyle.tomeito_api.messaging;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.CollectionList;
import util.CollectionStrictSync;
import util.DataSerializer;
import util.SneakyThrow;
import util.Validate;
import util.promise.IPromise;
import util.promise.Promise;
import util.reflect.Ref;
import xyz.acrylicstyle.authlib.GameProfile;
import xyz.acrylicstyle.authlib.properties.Property;
import xyz.acrylicstyle.authlib.properties.PropertyMap;
import xyz.acrylicstyle.minecraft.v1_8_R1.EntityPlayer;
import xyz.acrylicstyle.nmsapi.abstracts.utils.CraftUtils;
import xyz.acrylicstyle.shared.NMSAPI;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.shared.ChannelConstants;
import xyz.acrylicstyle.tomeito_api.utils.Callback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PluginChannelListener implements PluginMessageListener {
    @NotNull
    public static final PluginChannelListener pcl = new PluginChannelListener();

    @NotNull
    private final CollectionStrictSync<String, CollectionStrictSync<String, Callback<String>>> callbacks = new CollectionStrictSync<>();

    @NotNull
    private final CollectionList<String> registeredListeners = new CollectionList<>();

    // private PluginChannelListener() {} // todo: 0.7

    /**
     * An alias for {@link #pcl}.
     */
    @NotNull
    public static PluginChannelListener getInstance() { return pcl; }

    @SuppressWarnings("deprecation")
    @Override
    public void onPluginMessageReceived(String tag, org.bukkit.entity.Player player, byte[] message) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String subchannel = in.readUTF();
            String input = in.readUTF();
            if (tag.equals(ChannelConstants.PLAY_SOUND)) {
                UUID uuid;
                try {
                    uuid = UUID.fromString(subchannel);
                } catch (IllegalArgumentException e) {
                    return;
                }
                Player p = Bukkit.getPlayer(uuid);
                if (p != null) {
                    DataSerializer serializer = DataSerializer.fromString(input);
                    Sound sound = xyz.acrylicstyle.tomeito_api.sounds.Sound.tryResolveSound(serializer.get("sound").toString());
                    float volume = Float.parseFloat(serializer.get("volume").toString());
                    float pitch = Float.parseFloat(serializer.get("pitch").toString());
                    if (sound != null) p.playSound(p.getLocation(), sound, volume, pitch);
                }
                return;
            } else if (tag.equals(ChannelConstants.REFRESH_PLAYER)) {
                DataSerializer serializer = DataSerializer.fromString(input);
                UUID uSubchannel = UUID.fromString(subchannel);
                Player target = Bukkit.getPlayer(uSubchannel);
                GameProfile profile = new GameProfile(Ref.getClass(NMSAPI.getClassWithoutException("EntityHuman")).getMethod("getProfile").invokeObj(CraftUtils.getHandle(target)));
                System.out.println("Profile(Handle): " + profile.getHandle());
                PropertyMap prop = profile.getProperties();
                prop.removeAll("textures");
                prop.put("textures", new Property((String) serializer.get("name"), (String) serializer.get("value"), (String) serializer.get("signature")));
                profile.setProperties(prop);
                try {
                    new EntityPlayer(CraftUtils.getHandle(target)).setProfile(profile);
                } finally {
                    Bukkit.getOnlinePlayers().forEach(p -> {
                        if (!p.getUniqueId().equals(uSubchannel)) {
                            p.hidePlayer(target);
                            p.showPlayer(target);
                        }
                    });
                }
                return;
            }
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
    public void get(@NotNull org.bukkit.entity.Player p, @NotNull String subchannel, @Nullable String message, @NotNull String channel, @NotNull Callback<String> callback) {
        if (!callbacks.containsKey(channel)) {
            callbacks.put(channel, new CollectionStrictSync<>());
        }
        CollectionStrictSync<String, Callback<String>> callbacks2 = callbacks.get(channel);
        callbacks2.put(subchannel, callback);
        callbacks.put(channel, callbacks2);
        sendToBungeeCord(p, channel, subchannel, message);
    }

    /**
     * Sends plugin message and waits for up to 10 seconds.
     * @param p the player (sender)
     * @param subchannel the subchannel, if null, the player's uuid will be used.
     * @param message the message, if null, an empty string will be used.
     * @param tag the tag, also known as "channel"
     * @return the promise
     */
    public Promise<String> get(@NotNull Player p, @NotNull String tag, @Nullable String subchannel, @Nullable String message) {
        return get(p, tag, subchannel, message, 10000);
    }

    /**
     * Sends plugin message and waits for up to 10 seconds.
     * @param player the player (sender)
     * @param subchannel the subchannel, if null, the player's uuid will be used.
     * @param message the message, if null, an empty string will be used.
     * @param tag the tag, also known as "channel"
     * @return the promise
     */
    public <T extends Enum<T>> Promise<T> get(@NotNull Class<T> clazz, @NotNull Player player, @NotNull String tag, @Nullable String subchannel, @Nullable String message) {
        return get(clazz, player, tag, subchannel, message, 10000);
    }

    /**
     * Sends plugin message and waits for the response within the timeout.
     * @param player the player (sender)
     * @param subchannel the subchannel, if null, the player's uuid will be used.
     * @param message the message, if null, an empty string will be used.
     * @param tag the tag, also known as "channel"
     * @param timeout timeout in milliseconds
     * @return the promise that contains result (expects server to return results)
     */
    public <T extends Enum<T>> Promise<T> get(@NotNull Class<T> clazz, @NotNull Player player, @NotNull String tag, @Nullable String subchannel, @Nullable String message, int timeout) {
        return get(player, tag, subchannel, message, timeout).then((IPromise<String, T>) s -> Enum.valueOf(clazz, s));
    }

    /**
     * Sends plugin message and waits for the timeout.
     * @param player the player (sender)
     * @param subchannel the subchannel, if null, the player's uuid will be used.
     * @param message the message, if null, an empty string will be used.
     * @param tag the tag, also known as "channel"
     * @param timeout timeout in milliseconds
     * @return the promise that contains result (expects server to return results)
     */
    public Promise<String> get(@NotNull Player player, @NotNull String tag, @Nullable String subchannel, @Nullable String message, int timeout) {
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

    public void sendToBungeeCord(@NotNull org.bukkit.entity.Player p, @NotNull String tag, @Nullable String subchannel, @Nullable String message) {
        Validate.notNull(p, "player must not be null");
        Validate.notNull(subchannel, "subchannel cannot be null");
        Validate.notNull(message, "message cannot be null");
        Validate.notNull(tag, "tag cannot be null");
        if (!registeredListeners.contains(tag)) {
            Bukkit.getMessenger().registerIncomingPluginChannel(TomeitoAPI.getInstance(), tag, this);
            Bukkit.getMessenger().registerOutgoingPluginChannel(TomeitoAPI.getInstance(), tag);
            registeredListeners.add(tag);
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            if (subchannel != null) out.writeUTF(subchannel);
            if (message != null) out.writeUTF(message);
        } catch (IOException e) {
            // uh oh
        }
        p.sendPluginMessage(TomeitoAPI.getInstance(), tag, b.toByteArray());
    }
}
