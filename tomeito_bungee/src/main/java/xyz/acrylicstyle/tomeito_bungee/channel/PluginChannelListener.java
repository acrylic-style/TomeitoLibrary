package xyz.acrylicstyle.tomeito_bungee.channel;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import util.SneakyThrow;
import xyz.acrylicstyle.tomeito_api.shared.ChannelConstants;

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
            if (e.getTag().equals(ChannelConstants.PROTOCOL_VERSION)) {
                String subchannel = in.readUTF();
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(UUID.fromString(subchannel));
                in.readUTF();
                sendToBukkit(ChannelConstants.PROTOCOL_VERSION, subchannel, Integer.toString(player.getPendingConnection().getVersion()), player.getServer().getInfo());
            }
        } catch (IOException ex) {
            SneakyThrow.sneaky(ex);
        }
    }

    public static void sendToBukkit(String tag, String subchannel, String message, ServerInfo server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF(subchannel);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.sendData(tag, stream.toByteArray()); // channel = tag
    }
}
