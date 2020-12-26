package xyz.acrylicstyle.tomeito_api.nbs.v4;

import org.jetbrains.annotations.NotNull;
import util.nbs.NBSHeader;
import util.nbs.NBSLayerData;
import util.nbs.v4.NBS4File;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSFile;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSInstrument;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSTick;
import xyz.acrylicstyle.tomeito_api.utils.ListUtil;

import java.util.List;

public class BukkitNBS4File extends NBS4File implements BukkitNBSFile {
    public BukkitNBS4File(@NotNull NBSHeader header,
                          @NotNull List<BukkitNBSTick> ticks,
                          @NotNull List<NBSLayerData> layers,
                          @NotNull List<BukkitNBSInstrument> customInstruments) {
        super(header, ListUtil.downgrade(ticks), layers, ListUtil.downgrade(customInstruments));
    }

    @Override
    public @NotNull List<BukkitNBSTick> getBukkitTicks() {
        return ListUtil.upgrade(super.getTicks());
    }

    @Override
    public @NotNull List<BukkitNBSInstrument> getBukkitCustomInstruments() {
        return ListUtil.upgrade(super.getCustomInstruments());
    }
}
