package xyz.acrylicstyle.tomeito_api.utils;

public class Performance {
    private final long start;

    public Performance() {
        this.start = System.currentTimeMillis();
    }

    public void end() {
        long end = System.currentTimeMillis();
        Log.info("Took " + (end-start) + "ms");
    }
}
