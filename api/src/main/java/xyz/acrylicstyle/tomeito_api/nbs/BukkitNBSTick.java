package xyz.acrylicstyle.tomeito_api.nbs;

import org.jetbrains.annotations.NotNull;
import util.nbs.NBSTick;

import java.util.List;

public interface BukkitNBSTick extends NBSTick {
    @NotNull
    List<BukkitNBSNote> getBukkitLayers();
}
