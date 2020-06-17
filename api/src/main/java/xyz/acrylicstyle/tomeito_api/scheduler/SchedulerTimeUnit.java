package xyz.acrylicstyle.tomeito_api.scheduler;

public enum SchedulerTimeUnit {
    TICKS(1),
    SECONDS(20),
    MINUTES(20*60),
    HOURS(20*60*60),
    DAYS(20*60*60*24),
    ;

    public final long ticks;

    SchedulerTimeUnit(long ticks) {
        this.ticks = ticks;
    }
}
