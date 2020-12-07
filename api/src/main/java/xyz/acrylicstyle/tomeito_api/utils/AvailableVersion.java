package xyz.acrylicstyle.tomeito_api.utils;

/**
 * Simple marker annotation
 */
public @interface AvailableVersion {
    MinecraftVersion[] value();

    enum MinecraftVersion {
        ALL,
        SINCE_v1_12,
        SINCE_v1_14,
        v1_8,
        v1_9,
        v1_10,
        v1_11,
        v1_12,
        v1_13,
        v1_14,
        v1_15,
        v1_16,
        v1_17,
        v1_18,
        ;
    }
}
