package xyz.acrylicstyle.tomeito_api.utils;

import java.util.function.Supplier;

public class Runner {
    @SafeVarargs
    public static <T> T run(Supplier<T>... suppliers) {
        T last = null;
        for (Supplier<T> c : suppliers) last = c.get();
        return last;
    }

    @SafeVarargs
    public static <T> T Return(T... ts) {
        return ts[ts.length-1];
    }
}
