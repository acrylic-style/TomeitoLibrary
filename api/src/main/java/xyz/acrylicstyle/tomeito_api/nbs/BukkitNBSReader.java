package xyz.acrylicstyle.tomeito_api.nbs;

import org.jetbrains.annotations.NotNull;
import util.nbs.NBSReader;

import java.nio.ByteBuffer;
import java.util.List;

public interface BukkitNBSReader extends NBSReader {
    @NotNull
    List<BukkitNBSTick> readBukkitNotes(int layers, @NotNull ByteBuffer buffer);
}
