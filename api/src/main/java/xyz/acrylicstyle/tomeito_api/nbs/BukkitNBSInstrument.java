package xyz.acrylicstyle.tomeito_api.nbs;

import org.bukkit.Sound;
import org.jetbrains.annotations.Nullable;
import util.nbs.NBSInstrument;

public interface BukkitNBSInstrument extends NBSInstrument {
    @Nullable
    Sound getBukkitSound();
}
