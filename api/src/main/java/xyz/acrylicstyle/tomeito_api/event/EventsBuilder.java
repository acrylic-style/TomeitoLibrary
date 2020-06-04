package xyz.acrylicstyle.tomeito_api.event;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import util.CollectionList;

import java.util.Arrays;

/**
 * Helper methods (class) to make register events easier.
 * Not to be confused with {@link EventBuilder}, which will build ONE event.
 * This class will build many events with {@link EventBuilder}.
 */
public class EventsBuilder extends CollectionList<PendingEvent<?>> {
    public static EventsBuilder create() { return new EventsBuilder(); }

    @Override
    @NotNull
    public EventsBuilder addChain(@NotNull PendingEvent pendingEvent) {
        super.add(pendingEvent);
        return this;
    }

    @NotNull
    public EventsBuilder addAll(@NotNull PendingEvent<?>... events) {
        this.addAll(Arrays.asList(events));
        return this;
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
