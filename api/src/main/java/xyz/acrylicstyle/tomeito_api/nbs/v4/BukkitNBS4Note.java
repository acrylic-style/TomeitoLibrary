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
        byte key = super.getKey();
        if (key == 33) return 0.0F;
        if (key == 34) return 0.53F;
        if (key == 35) return 0.57F;
        if (key == 36) return 0.6F;
        if (key == 37) return 0.65F;
        if (key == 38) return 0.68F;
        if (key == 39) return 0.7F;
        if (key == 40) return 0.75F;
        if (key == 41) return 0.8F;
        if (key == 42) return 0.85F;
        if (key == 43) return 0.9F;
        if (key == 44) return 0.95F;
        if (key == 45) return 1.0F;
        if (key == 46) return 1.05F;
        if (key == 47) return 1.13F;
        if (key == 48) return 1.2F;
        if (key == 49) return 1.28F;
        if (key == 50) return 1.35F;
        if (key == 51) return 1.4F;
        if (key == 52) return 1.5F;
        if (key == 53) return 1.6F;
        if (key == 54) return 1.7F;
        if (key == 55) return 1.8F;
        if (key == 56) return 1.9F;
        if (key == 57) return 2.0F;
        return 0.0F;
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
