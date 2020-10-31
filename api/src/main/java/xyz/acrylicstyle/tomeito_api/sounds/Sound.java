package xyz.acrylicstyle.tomeito_api.sounds;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.ICollectionList;

public class Sound {
    public static final org.bukkit.Sound ENTITY_TNT_PRIMED;

    public static final org.bukkit.Sound BLOCK_NOTE_PLING;

    static {
        if (ICollectionList.asList(org.bukkit.Sound.values()).map(Enum::name).contains("BLOCK_NOTE_BLOCK_PLING")) {
            BLOCK_NOTE_PLING = org.bukkit.Sound.valueOf("BLOCK_NOTE_BLOCK_PLING");
        } else if (ICollectionList.asList(org.bukkit.Sound.values()).map(Enum::name).contains("BLOCK_NOTE_PLING")) {
            BLOCK_NOTE_PLING = org.bukkit.Sound.valueOf("BLOCK_NOTE_PLING");
        } else {
            BLOCK_NOTE_PLING = org.bukkit.Sound.valueOf("NOTE_PLING");
        }

        if (ICollectionList.asList(org.bukkit.Sound.values()).map(Enum::name).contains("FUSE")) {
            ENTITY_TNT_PRIMED = org.bukkit.Sound.valueOf("FUSE");
        } else {
            ENTITY_TNT_PRIMED = org.bukkit.Sound.valueOf("ENTITY_TNT_PRIMED");
        }
    }

    @Nullable
    public static org.bukkit.Sound tryResolveSound(@NotNull String sound) {
        String up = sound.toUpperCase().replaceAll("\\.", "_");
        if (up.equals("BLOCK_NOTE_BLOCK_PLING")) return BLOCK_NOTE_PLING;
        if (up.equals("BLOCK_NOTE_PLING")) return BLOCK_NOTE_PLING;
        if (up.equals("NOTE_PLING")) return BLOCK_NOTE_PLING;
        if (up.equals("FUSE")) return ENTITY_TNT_PRIMED;
        if (up.equals("ENTITY_TNT_PRIMED")) return ENTITY_TNT_PRIMED;
        return ICollectionList.asList(org.bukkit.Sound.values()).filter(s -> s.name().toUpperCase().equals(up)).first();
    }
}
