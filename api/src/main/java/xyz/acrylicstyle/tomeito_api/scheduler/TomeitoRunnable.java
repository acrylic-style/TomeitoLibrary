package xyz.acrylicstyle.tomeito_api.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

public abstract class TomeitoRunnable implements Runnable {
    private int id = -1;

    /**
     * Returns the task ID.
     * Returns -1 if not scheduled yet.
     * @return the task ID, -1 if not scheduled yet
     */
    public final int getTaskId() {
        return id;
    }

    /**
     * Cancel the execution of the task.
     * @throws IllegalArgumentException when couldn't find task by ID
     * @throws IllegalArgumentException when task is not scheduled yet
     */
    public final void cancel() {
        if (id == -1) throw new IllegalArgumentException("task isn't scheduled yet!");
        TomeitoAPI.getScheduler().cancelTask(id);
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
