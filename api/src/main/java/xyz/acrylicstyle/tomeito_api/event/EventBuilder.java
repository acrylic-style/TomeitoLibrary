package xyz.acrylicstyle.tomeito_api.event;

import util.Validate;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class EventBuilder<T extends Event> {
    @NotNull
    private static final Listener listener = new Listener() {};

    @NotNull private final Class<T> event;
    @Nullable private PendingEventExecutor<T> executor = null;
    @NotNull private EventPriority priority = EventPriority.NORMAL;
    @Nullable private Plugin plugin = null;
    private boolean ignoreCancelled = false;

    public EventBuilder(@NotNull Class<T> event) {
        Validate.notNull(event, "event cannot be null");
        this.event = event;
    }

    @NotNull
    public static <T extends Event> EventBuilder<T> builder(Class<T> event) { return new EventBuilder<>(event); }

    @NotNull
    public EventBuilder<T> priority(@NotNull EventPriority priority) {
        Validate.notNull(priority, "priority cannot be null");
        this.priority = priority;
        return this;
    }

    @NotNull
    public EventBuilder<T> executor(@NotNull PendingEventExecutor<T> executor) {
        Validate.notNull(executor, "executor cannot be null");
        this.executor = executor;
        return this;
    }

    @NotNull
    public EventBuilder<T> executor(@NotNull Consumer<T> executor) {
        Validate.notNull(executor, "executor cannot be null");
        this.executor = new PendingEventExecutor<T>() {
            @Override
            public void run(@NotNull T event) {
                executor.accept(event);
            }
        };
        return this;
    }

    @NotNull
    public EventBuilder<T> plugin(@NotNull Plugin plugin) {
        Validate.notNull(plugin, "plugin cannot be null");
        this.plugin = plugin;
        return this;
    }

    @NotNull
    public EventBuilder<T> ignoreCancelled(boolean ignoreCancelled) {
        this.ignoreCancelled = ignoreCancelled;
        return this;
    }

    public PendingEvent<T> build() {
        Validate.notNull(executor, "executor cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");
        assert executor != null;
        assert plugin != null;
        return new PendingEvent<>(event, listener, executor, priority, plugin, ignoreCancelled);
    }
}
