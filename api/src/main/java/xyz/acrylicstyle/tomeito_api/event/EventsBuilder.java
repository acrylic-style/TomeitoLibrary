package xyz.acrylicstyle.tomeito_api.event;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import util.CollectionList;

/**
 * Helper methods (class) to make register events easier.
 * Not to be confused with {@link EventBuilder}, which will build ONE event.
 * This class will build many events with {@link EventBuilder}.
 */
public class EventsBuilder extends CollectionList<PendingEvent<?>> {
    public static EventsBuilder create() { return new EventsBuilder(); }

    @Override
    public @NotNull EventsBuilder thenAdd(@NotNull PendingEvent<?> pendingEvent) {
        return (EventsBuilder) super.thenAdd(pendingEvent);
    }

    public void registerAll() {
        this.forEach(PendingEvent::register);
    }

    public void registerAll(@NotNull Plugin plugin) {
        this.forEach(event -> event.register(plugin));
    }

    public void registerAll(@NotNull Plugin plugin, boolean ignoreCancelled) {
        this.forEach(event -> event.register(plugin, ignoreCancelled));
    }
}
