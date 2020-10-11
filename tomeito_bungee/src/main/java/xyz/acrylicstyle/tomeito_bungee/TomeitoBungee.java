package xyz.acrylicstyle.tomeito_bungee;

import net.md_5.bungee.api.plugin.Plugin;
import xyz.acrylicstyle.tomeito_api.shared.ChannelConstants;
import xyz.acrylicstyle.tomeito_bungee.channel.PluginChannelListener;

public class TomeitoBungee extends Plugin {
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new PluginChannelListener());
        getProxy().registerChannel(ChannelConstants.PROTOCOL_VERSION);
        getLogger().info("Enabled TomeitoBungee");
    }
}
