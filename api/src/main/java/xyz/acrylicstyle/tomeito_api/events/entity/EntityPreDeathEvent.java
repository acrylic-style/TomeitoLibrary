package xyz.acrylicstyle.tomeito_api.events.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.acrylicstyle.tomeito_api.events.misc.PreDeathReason;

/**
 * Fired when entity is about to death. This event may be unreliable or inaccurate, so plugin should handle them correctly.
 * (e.g. damage was modified by plugin, but the EntityPreDeathEvent is still fired)
 */
public class EntityPreDeathEvent extends EntityEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;
    protected final Entity killer;
    protected final PreDeathReason reason;

    public EntityPreDeathEvent(@NotNull Entity what, @Nullable Entity killer, @NotNull PreDeathReason reason) {
        super(what);
        this.killer = killer;
        this.reason = reason;
    }

    public PreDeathReason getReason() {
        return reason;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Nullable
    public Entity getKiller() {
        return killer;
    }
}
