package xyz.acrylicstyle.tomeito_api.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

import java.util.Objects;

public abstract class TomeitoRunnable extends ForwardingTomeitoTask implements Runnable {
    private int id = -1;

    /**
     * Get the task associated with this runnable.
     * @throws IllegalStateException if the task isn't scheduled yet
     * @return the task associated with this runnable.
     */
    @NotNull
    @Override
    public final TomeitoTask delegate() {
        if (this.id == -1) throw new IllegalStateException("task isn't scheduled yet");
        return Objects.requireNonNull(TomeitoAPI.getScheduler().getTask(this.id));
    }

    @NotNull
    public final TomeitoTask runTask(@NotNull Plugin plugin) {
        return setupId(TomeitoAPI.getScheduler().runTask(plugin, this));
    }

    @NotNull
    public final TomeitoTask runTaskLater(@NotNull Plugin plugin, @NotNull SchedulerTimeUnit unit, long time) {
        return setupId(TomeitoAPI.getScheduler().runTaskLater(plugin, this, unit, time));
    }

    @NotNull
    public final TomeitoTask runTaskTimer(@NotNull Plugin plugin, @NotNull SchedulerTimeUnit delayUnit, long delayTime, @NotNull SchedulerTimeUnit timerUnit, long timerTime) {
        return setupId(TomeitoAPI.getScheduler().runTaskTimer(plugin, this, delayUnit, delayTime, timerUnit, timerTime));
    }

    @NotNull
    public final TomeitoTask runTaskAsynchronously(@NotNull Plugin plugin) {
        return setupId(TomeitoAPI.getScheduler().runTaskAsynchronously(plugin, this));
    }

    @NotNull
    public final TomeitoTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull SchedulerTimeUnit unit, long time) {
        return setupId(TomeitoAPI.getScheduler().runTaskLaterAsynchronously(plugin, this, unit, time));
    }

    @NotNull
    public final TomeitoTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull SchedulerTimeUnit delayUnit, long delayTime, @NotNull SchedulerTimeUnit timerUnit, long timerTime) {
        return setupId(TomeitoAPI.getScheduler().runTaskTimerAsynchronously(plugin, this, delayUnit, delayTime, timerUnit, timerTime));
    }

    @NotNull
    private TomeitoTask setupId(@NotNull TomeitoTask task) {
        this.id = task.getTaskId();
        return task;
    }
}
