package xyz.acrylicstyle.tomeito_api.nbs;

import org.jetbrains.annotations.Nullable;
import util.nbs.NBSNote;

public interface BukkitNBSNote extends NBSNote {
    /**
     * Returns the sound for this note.
     * This method may return null if the sound is not supported for this version of minecraft.
     * @return the sound
     */
    @Nullable
    org.bukkit.Sound getSound();
}
