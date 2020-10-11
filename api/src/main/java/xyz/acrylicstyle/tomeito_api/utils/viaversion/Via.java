package xyz.acrylicstyle.tomeito_api.utils.viaversion;

import org.jetbrains.annotations.Nullable;

public class Via {
    static Class<?> cachedClass = null;

    private static void init() {
        if (cachedClass == null) {
            try {
                cachedClass = Class.forName("us.myles.ViaVersion.api.Via");
            } catch (ClassNotFoundException ignored) {}
        }
    }

    @Nullable
    public static ViaAPI getAPI() {
        init();
        if (cachedClass == null) return null;
        return new ViaAPI();
    }
}
