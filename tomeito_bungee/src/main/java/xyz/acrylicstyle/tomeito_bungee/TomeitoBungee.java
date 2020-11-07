package xyz.acrylicstyle.tomeito_bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.connection.LoginResult;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import util.ReflectionHelper;
import util.promise.Promise;
import xyz.acrylicstyle.mcutil.mojang.MojangAPI;
import xyz.acrylicstyle.tomeito_api.shared.ChannelConstants;
import xyz.acrylicstyle.tomeito_bungee.channel.PluginChannelListener;

public class TomeitoBungee extends Plugin {
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new PluginChannelListener());
        getProxy().registerChannel(ChannelConstants.PROTOCOL_VERSION);
        getProxy().registerChannel(ChannelConstants.SET_SKIN);
        getLogger().info("Enabled TomeitoBungee");
    }

    @Contract(pure = true)
    @NotNull
    public static Promise<@NotNull Boolean> setSkin(ProxiedPlayer player, String nick) {
        InitialHandler handler = (InitialHandler) player.getPendingConnection();
        return MojangAPI.getUniqueId(nick).then(uuid -> {
            if (uuid == null) return false;
            return MojangAPI.getGameProfile(uuid).then(profile -> {
                ReflectionHelper.setFieldWithoutException(InitialHandler.class, handler, "loginProfile", new LoginResult(profile.id.toString(), profile.name, new LoginResult.Property[]{
                        new LoginResult.Property(profile.properties[0].name, profile.properties[0].value, profile.properties[0].signature)
                }));
                return true;
            }).complete();
        });
    }
}
