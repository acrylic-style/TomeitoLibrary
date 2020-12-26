package xyz.acrylicstyle.tomeito_api.nbs;

import org.jetbrains.annotations.NotNull;
import util.nbs.NBSFile;

import java.util.List;

public interface BukkitNBSFile extends NBSFile {
    @NotNull
    List<BukkitNBSTick> getBukkitTicks();

    @NotNull
    List<BukkitNBSInstrument> getBukkitCustomInstruments();
}
