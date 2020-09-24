package xyz.acrylicstyle.tomeito_api.event;

import util.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PendingEvent<T extends Event> {
    @NotNull private final Class<? extends Event> event;
    @NotNull private final Listener listener;
    @NotNull private final PendingEventExecutor<T> executor;
    @NotNull private final EventPriority priority;
    @NotNull private final Plugin plugin;
    private final boolean ignoreCancelled;

    public PendingEvent(@NotNull Class<T> event,
                        @NotNull Listener listener,
                        @NotNull PendingEventExecutor<T> executor,
                        @NotNull EventPriority priority,
                        @NotNull Plugin plugin,
                        boolean ignoreCancelled) {
        Validate.notNull(event, "event cannot be null");
        Validate.notNull(listener, "listener cannot be null");
        Validate.notNull(executor, "executor cannot be null");
        Validate.notNull(priority, "priority cannot be null");
        Validate.notNull(plugin, "plugin cannot be null");
        this.event = event;
        this.listener = listener;
        this.executor = executor;
        this.priority = priority;
        this.plugin = plugin;
        this.ignoreCancelled = ignoreCancelled;
    }

    public boolean isIgnoreCancelled() { return ignoreCancelled; }

    @NotNull
    public Class<? extends Event> getEvent() { return event; }

    @NotNull
    public Listener getListener() { return listener; }

    @NotNull
    public EventPriority getPriority() { return priority; }

    @NotNull
    public PendingEventExecutor<T> getExecutor() { return executor; }

    @NotNull
    public Plugin getPlugin() { return plugin; }

    public void register() { register(getPlugin(), isIgnoreCancelled()); }

    public void register(@NotNull Plugin plugin) { register(plugin, isIgnoreCancelled()); }

    public void register(@NotNull Plugin plugin, boolean ignoreCancelled) {
        Validate.notNull(plugin, "plugin cannot be null");
        Bukkit.getPluginManager().registerEvent(getEvent(), getListener(), getPriority(), getExecutor(), plugin, ignoreCancelled);
    }
}
