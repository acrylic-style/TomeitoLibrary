package xyz.acrylicstyle.tomeito_api.utils.viaversion;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import util.reflect.Ref;
import util.reflect.RefMethod;

public class ViaAPI {
    private final RefMethod<Object> getPlayerVersion = Ref.forName("us.myles.ViaVersion.api.ViaAPI").getMethod("getPlayerVersion", Player.class);
    
    private final Object via = Ref.forName("us.myles.ViaVersion.api.Via").getMethod("getAPI").invoke(null);
    
    public int getPlayerVersion(@NotNull Player player) { return (int) getPlayerVersion.invoke(via, player); }
}
