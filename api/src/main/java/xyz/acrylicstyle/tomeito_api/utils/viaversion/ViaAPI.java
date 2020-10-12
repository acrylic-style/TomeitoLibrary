package xyz.acrylicstyle.tomeito_api.utils.viaversion;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import util.reflect.Ref;
import util.reflect.RefMethod;

import java.util.UUID;

public class ViaAPI {
    private final RefMethod<Object> getPlayerVersion = Ref.forName("us.myles.ViaVersion.api.ViaAPI").getMethod("getPlayerVersion", UUID.class);
    
    private final Object via = Ref.forName("us.myles.ViaVersion.api.Via").getMethod("getAPI").invoke(null);
    
    public int getPlayerVersion(@NotNull Player player) { return (int) getPlayerVersion.invoke(via, player.getUniqueId()); }
}
