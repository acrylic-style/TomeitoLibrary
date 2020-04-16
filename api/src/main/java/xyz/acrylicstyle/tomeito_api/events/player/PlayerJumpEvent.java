package xyz.acrylicstyle.tomeito_api.events.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Fired when player jumps.
 */
public class PlayerJumpEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;
    protected Location from;
    protected Location to;

    public PlayerJumpEvent(@NotNull Player player, @NotNull Location from, @NotNull Location to) {
        super(player);
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
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

    @NotNull
    public Location getFrom() {
        return from;
    }

    public void setFrom(@NotNull Location from) {
        this.from = from;
    }

    @NotNull
    public Location getTo() {
        return to;
    }
}
