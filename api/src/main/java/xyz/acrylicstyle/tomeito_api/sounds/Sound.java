package xyz.acrylicstyle.tomeito_api.sounds;

import util.ICollectionList;

public class Sound {
    public static final org.bukkit.Sound FUSE;

    static {
        if (ICollectionList.asList(org.bukkit.Sound.values()).map(Enum::name).contains("FUSE")) {
            FUSE = org.bukkit.Sound.valueOf("FUSE");
        } else {
            FUSE = org.bukkit.Sound.valueOf("ENTITY_TNT_PRIMED");
        }
    }
}
