package xyz.acrylicstyle.tomeito_api.nbs.v4;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.nbs.v4.NBS4Instrument;
import xyz.acrylicstyle.tomeito_api.nbs.BukkitNBSInstrument;

// todo: support 'key' when playing note
public class BukkitNBS4Instrument extends NBS4Instrument implements BukkitNBSInstrument {
    public BukkitNBS4Instrument(@NotNull String name, @NotNull String sound, byte key, byte showKeyPress) {
        super(name, sound, key, showKeyPress);
    }

    @Override
    public @Nullable Sound getBukkitSound() {
        String sound = getSound().replaceAll("^(.*)\\.ogg$", "$1").replaceAll("\\.", "_");
        return xyz.acrylicstyle.tomeito_api.sounds.Sound.tryResolveSound(sound);
    }
}
