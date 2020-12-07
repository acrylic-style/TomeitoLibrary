package xyz.acrylicstyle.tomeito_api.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TomeitoScheduler {
    /**
     * Get task by id.
     * @param id the task id
     * @return task if found, null otherwise
     */
    @Nullable
    public abstract TomeitoTask getTask(int id);

    @NotNull
    public abstract TomeitoTask runTask(@NotNull Plugin plugin, @NotNull Runnable runnable);

    @NotNull
    public abstract TomeitoTask runTask(@NotNull Runnable runnable);

    @NotNull
    public abstract TomeitoTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable runnable, @NotNull SchedulerTimeUnit unit, long time);

    @NotNull
    public abstract TomeitoTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable runnable, @NotNull SchedulerTimeUnit delayUnit, long delayTime, @NotNull SchedulerTimeUnit timerUnit, long timerTime);

    @NotNull
    public abstract TomeitoTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable);

    @NotNull
    public abstract TomeitoTask runTaskAsynchronously(@NotNull Runnable runnable);

    @NotNull
    public abstract TomeitoTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable, @NotNull SchedulerTimeUnit unit, long time);

    @NotNull
    public abstract TomeitoTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable, @NotNull SchedulerTimeUnit delayUnit, long delayTime, @NotNull SchedulerTimeUnit timerUnit, long timerTime);

    /**
     * Cancels the task specified by ID.
     * @param id the id
     * @throws IllegalArgumentException when couldn't find task by ID
     */
    public abstract void cancelTask(int id) throws IllegalArgumentException;
}
