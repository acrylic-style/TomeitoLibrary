package xyz.acrylicstyle.tomeito_api.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

public abstract class PendingEventExecutor<T extends Event> implements EventExecutor {
    @SuppressWarnings("unchecked")
    @Override
    public final void execute(Listener listener, Event event) throws EventException { run((T) event); }

    public abstract void run(@NotNull T event) throws EventException;
}
