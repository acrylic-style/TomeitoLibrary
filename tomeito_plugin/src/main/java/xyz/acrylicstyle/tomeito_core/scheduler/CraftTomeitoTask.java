package xyz.acrylicstyle.tomeito_core.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.scheduler.SchedulerTimeUnit;
import xyz.acrylicstyle.tomeito_api.scheduler.TomeitoTask;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class CraftTomeitoTask implements TomeitoTask {
    private final int taskId;
    private final Plugin owner;
    private final Runnable runnable;
    private final boolean sync;
    private final boolean repeatable;
    private final Map.Entry<SchedulerTimeUnit, Long> delay;
    private final Map.Entry<SchedulerTimeUnit, Long> repeat;
    private boolean cancelled = false;
    public boolean hasRunDelayed = false; // todo: rename poorly named field
    public final AtomicLong cycle = new AtomicLong();

    public CraftTomeitoTask(int taskId,
                            @NotNull Plugin plugin,
                            @NotNull Runnable runnable,
                            boolean sync,
                            boolean repeatable,
                            @Nullable Map.Entry<SchedulerTimeUnit, Long> delay,
                            @Nullable Map.Entry<SchedulerTimeUnit, Long> repeat) {
        this.taskId = taskId;
        this.owner = plugin;
        this.runnable = runnable;
        this.sync = sync;
        this.repeatable = repeatable;
        this.delay = delay;
        this.repeat = repeat;
    }

    @Override
    public void cancel() {
        cancelled = true;
        ((CraftTomeitoScheduler) TomeitoAPI.getScheduler()).removeTask(taskId);
    }

    @Override
    public boolean isCancelled() { return cancelled; }

    @Override
    public int getTaskId() { return taskId; }

    @Override
    public boolean isSync() { return sync; }

    @Nullable
    @Override
    public Map.Entry<SchedulerTimeUnit, Long> getDelayTime() { return delay; }

    @Nullable
    @Override
    public Map.Entry<SchedulerTimeUnit, Long> getRepeatTime() { return repeat; }

    @Override
    public boolean isRepeatable() { return repeatable; }

    @Override
    public @NotNull Plugin getOwner() { return owner; }

    @NotNull
    @Override
    public Runnable getRunnable() { return runnable; }

    @Override
    public long getCycle() { return cycle.get(); }

    @Override
    public String toString() {
        return "CraftTomeitoTask{" + "taskId=" + taskId +
                ", owner=" + owner +
                ", runnable=" + runnable +
                ", sync=" + sync +
                ", repeatable=" + repeatable +
                ", delay=" + delay +
                ", repeat=" + repeat +
                ", cancelled=" + cancelled +
                ", cycle=" + cycle +
                '}';
    }
}
