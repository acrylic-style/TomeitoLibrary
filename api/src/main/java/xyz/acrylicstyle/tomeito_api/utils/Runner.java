package xyz.acrylicstyle.tomeito_api.utils;

import java.util.function.Supplier;

/**
 * @deprecated meaningless class - why not use {@link util.promise.Promise Promise}?
 */
@Deprecated
public class Runner {
    @Deprecated
    @SafeVarargs
    public static <T> T run(Supplier<T>... suppliers) {
        T last = null;
        for (Supplier<T> c : suppliers) last = c.get();
        return last;
    }

    /**
     * Returns the last element of the array.
     */
    @Deprecated
    @SafeVarargs
    public static <T> T Return(T... ts) {
        return ts[ts.length-1];
    }
}
