package xyz.acrylicstyle.tomeito_api.utils;

/**
 * @deprecated garbage thing, use {@link util.Performance} instead
 */
@Deprecated
public class Performance {
    private final long start;

    @Deprecated
    public Performance() {
        this.start = System.currentTimeMillis();
    }

    @Deprecated
    public void end() {
        long end = System.currentTimeMillis();
        Log.info("Took " + (end-start) + "ms");
    }
}
