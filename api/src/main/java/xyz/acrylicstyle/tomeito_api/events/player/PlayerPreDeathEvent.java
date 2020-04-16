package xyz.acrylicstyle.tomeito_api.events.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.acrylicstyle.tomeito_api.events.misc.PreDeathReason;

/**
 * Fired when player is about to death.
 */
public class PlayerPreDeathEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;
    protected Entity killer;
    protected PreDeathReason reason;

    public PlayerPreDeathEvent(@NotNull Player who, @Nullable Entity killer, @NotNull PreDeathReason reason) {
        super(who);
        this.killer = killer;
        this.reason = reason;
    }

    @NotNull
    public PreDeathReason getReason() {
        return reason;
    }

    @NotNull
    @Override
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
