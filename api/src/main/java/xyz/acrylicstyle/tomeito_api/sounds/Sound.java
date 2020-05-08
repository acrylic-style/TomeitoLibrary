package xyz.acrylicstyle.tomeito_api.sounds;

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
}
