package xyz.acrylicstyle.tomeito_api.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import util.Validate;
import util.promise.IPromise;
import util.promise.Promise;
import xyz.acrylicstyle.tomeito_api.messaging.PluginChannelListener;
import xyz.acrylicstyle.tomeito_api.shared.ChannelConstants;
import xyz.acrylicstyle.tomeito_api.utils.viaversion.Via;
import xyz.acrylicstyle.tomeito_api.utils.viaversion.ViaAPI;

public class ProtocolVersionRetriever {
    public static @NotNull Promise<@NotNull Integer> getProtocolVersion(@NotNull Player player) {
        Validate.notNull(player, "player cannot be null");
        ViaAPI api = Via.getAPI();
        if (api == null) return getProtocolVersion(player, Type.BUNGEECORD);
        return Promise.of(api.getPlayerVersion(player));
    }

    public static @NotNull Promise<@NotNull Integer> getProtocolVersion(@NotNull Player player, @NotNull Type type) {
        Validate.notNull(player, "player cannot be null");
        Validate.notNull(type, "type cannot be null");
        if (type == Type.BUNGEECORD) {
            return PluginChannelListener.pcl.get(player, ChannelConstants.PROTOCOL_VERSION, null, null, 1500).then((IPromise<String, Integer>) Integer::parseInt);
        } else {
            ViaAPI api = Via.getAPI();
            if (api == null) throw new RuntimeException("ViaVersion is not available.");
            return Promise.of(api.getPlayerVersion(player));
        }
    }

    public enum Type {
        BUNGEECORD,
        VIAVERSION,
    }
}
