package xyz.acrylicstyle.tomeito_api.nbs.v4;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.ICollectionList;
import util.nbs.NBSHeader;
import util.nbs.v4.NBS4Tick;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSNote;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSTick;
import xyz.acrylicstyle.tomeito_api.utils.ListUtil;

import java.util.List;

public class BukkitNBS4Tick extends NBS4Tick implements BukkitNBSTick {
    protected final NBSHeader header;

    public BukkitNBS4Tick(NBSHeader file, int startingTick, @NotNull List<BukkitNBSNote> layers) {
        super(startingTick, ListUtil.downgrade(layers));
        this.header = file;
    }

    @Override
    public @NotNull List<@Nullable BukkitNBSNote> getBukkitLayers() { return ListUtil.upgrade(layers); }

    @SuppressWarnings("NullableProblems")
    @Override
    public @NotNull List<@NotNull BukkitNBSNote> getPlayableBukkitLayers() {
        return ICollectionList.asList(getBukkitLayers()).nonNull();
    }

    @NotNull
    @Override
    public NBSHeader getHeader() { return header; }

    @Override
    public int getTick() {
        return Math.round(super.getStartingTick() / (header.getTempo() / 2000F));
    }
}
