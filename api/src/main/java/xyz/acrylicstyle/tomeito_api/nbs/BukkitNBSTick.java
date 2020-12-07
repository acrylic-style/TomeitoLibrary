package xyz.acrylicstyle.tomeito_api.nbs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.nbs.NBSHeader;
import util.nbs.NBSTick;

import java.util.List;

public interface BukkitNBSTick extends NBSTick {
    @NotNull
    NBSHeader getHeader();

    @NotNull
    List<@Nullable BukkitNBSNote> getBukkitLayers();

    @NotNull
    List<@NotNull BukkitNBSNote> getPlayableBukkitLayers();

    int getTick();
}
