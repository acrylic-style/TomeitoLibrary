package xyz.acrylicstyle.tomeito_bungee.channel;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.Nullable;
import util.SneakyThrow;
import util.serialization.ClassSerializer;
import xyz.acrylicstyle.mcutil.mojang.Property;
import xyz.acrylicstyle.tomeito_api.shared.ChannelConstants;
import xyz.acrylicstyle.tomeito_bungee.TomeitoBungee;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PluginChannelListener implements Listener {
    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            switch (e.getTag()) {
                case ChannelConstants.PROTOCOL_VERSION: {
                    String subchannel = in.readUTF();
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(subchannel));
                    sendToBukkit(ChannelConstants.PROTOCOL_VERSION, subchannel, Integer.toString(player.getPendingConnection().getVersion()), player.getServer().getInfo());
                    break;
                }
                case ChannelConstants.SET_SKIN: {
                    UUID uuid = UUID.fromString(in.readUTF());
                    String nick = in.readUTF();
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                    if (player == null) return;
                    TomeitoBungee.setSkin(player, nick).then(result -> {
                        System.out.println("Nicked " + player.getName() + " to " + nick + ", and result was " + (result == null ? "fail" : "success") + ".");
                        if (result != null) {
                            Property property = new Property(result.getName(), result.getValue(), result.getSignature());
                            sendToBukkit(ChannelConstants.REFRESH_PLAYER, uuid.toString(), new ClassSerializer<>(property).serialize(), player.getServer().getInfo());
                        }
                        return null;
                    }).queue();
                    break;
                }
                case ChannelConstants.SET_PROPERTY: {
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(in.readUTF()));
                    if (player == null) return;
                    Property property = ClassSerializer.deserialize(Property.class, in.readUTF());
                    TomeitoBungee.setProperty(player, property);
                    sendToBukkit(ChannelConstants.REFRESH_PLAYER, player.getUniqueId().toString(), new ClassSerializer<>(property).serialize(), player.getServer().getInfo());
                    break;
                }
            }
        } catch (IOException ex) {
            SneakyThrow.sneaky(ex);
        }
    }

    public static void sendToBukkit(String tag, @Nullable String subchannel, @Nullable String message, ServerInfo server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            if (subchannel != null) out.writeUTF(subchannel);
            if (message != null) out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.sendData(tag, stream.toByteArray()); // channel = tag
    }
}
