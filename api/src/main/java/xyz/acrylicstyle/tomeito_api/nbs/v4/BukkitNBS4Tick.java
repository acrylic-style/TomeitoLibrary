package xyz.acrylicstyle.tomeito_api.nbs.v4;

import org.jetbrains.annotations.NotNull;
import util.nbs.v4.NBS4Tick;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSNote;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSTick;
import xyz.acrylicstyle.tomeito_api.utils.ListUtil;

import java.util.List;

public class BukkitNBS4Tick extends NBS4Tick implements BukkitNBSTick {
    public BukkitNBS4Tick(int startingTick, @NotNull List<BukkitNBSNote> layers) {
        super(startingTick, ListUtil.downgrade(layers));
    }

    @Override
    public @NotNull List<BukkitNBSNote> getBukkitLayers() {
        return ListUtil.upgrade(layers);
    }
}
