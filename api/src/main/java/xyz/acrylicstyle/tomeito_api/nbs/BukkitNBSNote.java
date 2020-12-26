package xyz.acrylicstyle.tomeito_api.nbs;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.nbs.NBSNote;

import java.util.List;

public interface BukkitNBSNote extends NBSNote {
    @NotNull
    List<BukkitNBSInstrument> getCustomInstruments();

    void setCustomInstruments(@NotNull List<BukkitNBSInstrument> customInstruments);

    /**
     * Returns the sound for this note.
     * This method may return null if the sound is not supported for this version of minecraft.
     * @return the sound
     */
    @Nullable
    org.bukkit.Sound getSound();

    float getSoundPitch();

    void play(@NotNull Player player);
}
