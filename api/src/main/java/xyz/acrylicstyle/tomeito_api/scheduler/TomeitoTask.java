package xyz.acrylicstyle.tomeito_api.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface TomeitoTask {
    /**
     * Get the task associated with this runnable.
     * @return the task associated with this runnable.
     */
    @NotNull
    default TomeitoTask getTask() { return this; }

    /**
     * Cancel the execution of this task.
     */
    void cancel();

    /**
     * @return whether the task was canceled.
     */
    boolean isCancelled();

    /**
     * Returns the task ID.
     * @return the task ID
     */
    int getTaskId();

    /**
     * @return true if this task is synchronized, false otherwise.
     */
    boolean isSync();

    default boolean isAsync() { return !isSync(); }

    /**
     * Get repeat time for this task.
     * @return the time or null if this isn't repeatable task
     */
    @Nullable
    Map.Entry<SchedulerTimeUnit, Long> getDelayTime();

    /**
     * Get delay time for this task.
     * @return the time or null if this isn't delayed task
     */
    @Nullable
    Map.Entry<SchedulerTimeUnit, Long> getRepeatTime();

    boolean isRepeatable();

    /**
     * Returns the owner of this task.
     * @return the plugin
     */
    @NotNull
    Plugin getOwner();

    @NotNull
    Runnable getRunnable();

    long getCycle();
}
