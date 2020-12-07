package xyz.acrylicstyle.tomeito_api.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Validate;

import java.util.Map;

public abstract class ForwardingTomeitoTask implements TomeitoTask {
    @NotNull
    public abstract TomeitoTask delegate();

    @Override
    public void cancel() { delegate().cancel(); }

    @Override
    public boolean isCancelled() { return delegate().isCancelled(); }

    @Override
    public int getTaskId() { return delegate().getTaskId(); }

    @Override
    public boolean isSync() { return delegate().isSync(); }

    @Nullable
    @Override
    public Map.Entry<SchedulerTimeUnit, Long> getDelayTime() { return delegate().getDelayTime(); }

    @Nullable
    @Override
    public Map.Entry<SchedulerTimeUnit, Long> getRepeatTime() { return delegate().getRepeatTime(); }

    @Override
    public boolean isRepeatable() { return delegate().isRepeatable(); }

    @Override
    public @NotNull Plugin getOwner() { return delegate().getOwner(); }

    @Override
    public @NotNull Runnable getRunnable() { return delegate().getRunnable(); }

    @Override
    public long getCycle() { return delegate().getCycle(); }

    @NotNull
    public static ForwardingTomeitoTask getInstance(@NotNull TomeitoTask task) {
        Validate.notNull(task, "task cannot be null");
        return new ForwardingTomeitoTask() {
            @Override
            public @NotNull TomeitoTask delegate() {
                return task;
            }
        };
    }
}
