package xyz.acrylicstyle.tomeito_core.scheduler;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Collection;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.scheduler.SchedulerTimeUnit;
import xyz.acrylicstyle.tomeito_api.scheduler.TomeitoScheduler;
import xyz.acrylicstyle.tomeito_api.scheduler.TomeitoTask;

import java.util.AbstractMap;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("DuplicatedCode")
public class CraftTomeitoScheduler extends TomeitoScheduler {
    public final AtomicInteger taskId = new AtomicInteger();
    public final Collection<Integer, TomeitoTask> tasks = new Collection<>();
    public long cycle = 0;
    public long asyncCycle = 0;

    @Override
    public @Nullable TomeitoTask getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public @NotNull TomeitoTask runTask(@NotNull Plugin plugin, @NotNull Runnable runnable) {
        int id = taskId.getAndIncrement();
        CraftTomeitoTask task = new CraftTomeitoTask(id, plugin, runnable, true, false, null, null);
        tasks.add(id, task);
        return task;
    }

    @Override
    public @NotNull TomeitoTask runTask(@NotNull Runnable runnable) {
        return runTask(TomeitoAPI.getInstance(), runnable);
    }

    @Override
    public @NotNull TomeitoTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable runnable, @NotNull SchedulerTimeUnit unit, long time) {
        if (time < 1) throw new IllegalArgumentException("Time cannot be lower than 1");
        int id = taskId.getAndIncrement();
        CraftTomeitoTask task = new CraftTomeitoTask(id, plugin, runnable, true, false, new AbstractMap.SimpleImmutableEntry<>(unit, time), null);
        tasks.add(id, task);
        return task;
    }

    @Override
    public @NotNull TomeitoTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable runnable, @NotNull SchedulerTimeUnit delayUnit, long delayTime, @NotNull SchedulerTimeUnit timerUnit, long timerTime) {
        if (delayTime < 1) throw new IllegalArgumentException("delayTime cannot be lower than 1");
        if (timerTime < 1) throw new IllegalArgumentException("timerTime cannot be lower than 1");
        int id = taskId.getAndIncrement();
        CraftTomeitoTask task = new CraftTomeitoTask(
                id,
                plugin,
                runnable,
                true,
                true,
                new AbstractMap.SimpleImmutableEntry<>(delayUnit, delayTime),
                new AbstractMap.SimpleImmutableEntry<>(timerUnit, timerTime)
        );
        tasks.add(id, task);
        return task;
    }

    @Override
    public @NotNull TomeitoTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable) {
        int id = taskId.getAndIncrement();
        CraftTomeitoTask task = new CraftTomeitoTask(id, plugin, runnable, false, false, null, null);
        tasks.add(id, task);
        return task;
    }

    @Override
    public @NotNull TomeitoTask runTaskAsynchronously(@NotNull Runnable runnable) {
        return runTaskAsynchronously(TomeitoAPI.getInstance(), runnable);
    }

    @Override
    public @NotNull TomeitoTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable, @NotNull SchedulerTimeUnit unit, long time) {
        if (time < 1) throw new IllegalArgumentException("Time cannot be lower than 1");
        int id = taskId.getAndIncrement();
        CraftTomeitoTask task = new CraftTomeitoTask(id, plugin, runnable, false, false, new AbstractMap.SimpleImmutableEntry<>(unit, time), null);
        tasks.add(id, task);
        return task;
    }

    @Override
    public @NotNull TomeitoTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Runnable runnable, @NotNull SchedulerTimeUnit delayUnit, long delayTime, @NotNull SchedulerTimeUnit timerUnit, long timerTime) {
        if (delayTime < 1) throw new IllegalArgumentException("delayTime cannot be lower than 1");
        if (timerTime < 1) throw new IllegalArgumentException("timerTime cannot be lower than 1");
        int id = taskId.getAndIncrement();
        CraftTomeitoTask task = new CraftTomeitoTask(
                id,
                plugin,
                runnable,
                false,
                true,
                new AbstractMap.SimpleImmutableEntry<>(delayUnit, delayTime),
                new AbstractMap.SimpleImmutableEntry<>(timerUnit, timerTime)
        );
        tasks.add(id, task);
        return task;
    }

    @Override
    public void cancelTask(int id) {
        TomeitoTask task = getTask(id);
        if (task == null) throw new IllegalArgumentException("task " + id + " could not be found");
        task.cancel();
    }

    public void tick() {
        // remove cancelled tasks
        tasks.clone()
                .filter(TomeitoTask::isSync)
                .filter(TomeitoTask::isCancelled)
                .forEach((i, task) -> tasks.remove(i));
        // increase tick count
        tasks.clone()
                .filter(TomeitoTask::isSync)
                .forEach((i, task) -> ((CraftTomeitoTask) task).cycle.incrementAndGet());
        // run tasks and remove if it isn't repeatable
        tasks.clone()
                .filter(TomeitoTask::isSync)
                .filter(task -> !((CraftTomeitoTask) task).hasRunDelayed)
                .filter(task -> task.getDelayTime() == null || ((CraftTomeitoTask) task).cycle.get() % (task.getDelayTime().getKey().ticks * task.getDelayTime().getValue()) == 0)
                .forEach((i, task) -> {
                    ((CraftTomeitoTask) tasks.get(i)).hasRunDelayed = true;
                    task.getRunnable().run();
                    if (!task.isRepeatable()) tasks.remove(i);
                });
        // run tasks and remove if it isn't repeatable
        tasks.clone()
                .filter(TomeitoTask::isSync)
                .filter(TomeitoTask::isRepeatable)
                .filter(task -> ((CraftTomeitoTask) task).hasRunDelayed)
                .filter(task -> task.getRepeatTime() != null && ((CraftTomeitoTask) task).cycle.get() % (task.getRepeatTime().getKey().ticks * task.getRepeatTime().getValue()) == 0)
                .forEach((i, task) -> task.getRunnable().run());
        cycle++;
    }

    public void tickAsync() {
        // remove cancelled tasks
        tasks.clone()
                .filter(TomeitoTask::isAsync)
                .filter(TomeitoTask::isCancelled)
                .forEach((i, task) -> tasks.remove(i));
        // increase tick count
        tasks.clone()
                .filter(TomeitoTask::isAsync)
                .forEach((i, task) -> ((CraftTomeitoTask) task).cycle.incrementAndGet());
        tasks.clone()
                .filter(TomeitoTask::isAsync)
                .filter(task -> !((CraftTomeitoTask) task).hasRunDelayed)
                .filter(task -> task.getDelayTime() == null || ((CraftTomeitoTask) task).cycle.get() % (task.getDelayTime().getKey().ticks * task.getDelayTime().getValue()) == 0)
                .forEach((i, task) -> {
                    ((CraftTomeitoTask) tasks.get(i)).hasRunDelayed = true;
                    task.getRunnable().run();
                    if (!task.isRepeatable()) tasks.remove(i);
                });
        tasks.clone()
                .filter(TomeitoTask::isAsync)
                .filter(TomeitoTask::isRepeatable)
                .filter(task -> ((CraftTomeitoTask) task).hasRunDelayed)
                .filter(task -> task.getRepeatTime() != null && ((CraftTomeitoTask) task).cycle.get() % (task.getRepeatTime().getKey().ticks * task.getRepeatTime().getValue()) == 0)
                .forEach((i, task) -> task.getRunnable().run());
        asyncCycle++;
    }
}
