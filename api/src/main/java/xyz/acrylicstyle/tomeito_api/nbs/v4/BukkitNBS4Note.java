package xyz.acrylicstyle.tomeito_api.nbs.v4;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import util.nbs.v4.NBS4Note;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSNote;
import xyz.acrylicstyle.tomeito_api.sounds.Sound;

public class BukkitNBS4Note extends NBS4Note implements BukkitNBSNote {
    public static final int OFFSET = 33;

    public BukkitNBS4Note(byte instrument, byte key, byte volume, byte panning, short pitch) {
        super(instrument, key, volume, panning, pitch);
    }

    /**
     * Returns the sound for this note.
     * This method may return null if the sound is not supported for this version of minecraft.
     * @return the sound
     */
    @Nullable
    @Override
    public org.bukkit.Sound getSound() { return readInstrument(instrument); }

    @Override
    public float getSoundPitch() {
        return (super.getPitch() - OFFSET) / 10F;
    }

    /**
     * Returns the sound for the instrument.
     * This method may return null if the sound is not supported for this version of minecraft.
     * @return the sound
     */
    @Nullable
    @Contract(pure = true)
    public static org.bukkit.Sound readInstrument(int instrument) {
        if (instrument == 0) return Sound.BLOCK_NOTE_HARP;
        if (instrument == 1) return Sound.BLOCK_NOTE_BASS;
        if (instrument == 2) return Sound.BLOCK_NOTE_BASS_DRUM;
        if (instrument == 3) return Sound.BLOCK_NOTE_SNARE_DRUM;
        if (instrument == 4) return Sound.BLOCK_NOTE_STICKS;
        if (instrument == 5) return Sound.BLOCK_NOTE_BASS_GUITAR;
        if (instrument == 6) return Sound.BLOCK_NOTE_FLUTE;
        if (instrument == 7) return Sound.BLOCK_NOTE_BELL;
        if (instrument == 8) return Sound.BLOCK_NOTE_CHIME;
        if (instrument == 9) return Sound.BLOCK_NOTE_XYLOPHONE;
        if (instrument == 10) return Sound.BLOCK_NOTE_IRON_XYLOPHONE;
        if (instrument == 11) return Sound.BLOCK_NOTE_COW_BELL;
        if (instrument == 12) return Sound.BLOCK_NOTE_DIDGERIDOO;
        if (instrument == 13) return Sound.BLOCK_NOTE_BIT;
        if (instrument == 14) return Sound.BLOCK_NOTE_BANJO;
        if (instrument == 15) return Sound.BLOCK_NOTE_PLING;
        throw new IndexOutOfBoundsException("Instrument is out of range, unsupported version?");
    }
}
