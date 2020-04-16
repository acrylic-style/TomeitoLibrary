package xyz.acrylicstyle.tomeito_api.events.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when TNT block is about to turn into {@link org.bukkit.entity.TNTPrimed}
 * <br />
 * Cancelling it won't turn TNT into {@link org.bukkit.entity.TNTPrimed}
 * and leaves the TNT block as-is
 */
public class PlayerTNTPrimeEvent extends TNTPrimeEvent implements Cancellable {
    private boolean cancelled = false;
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player primerEntity;

    public PlayerTNTPrimeEvent(@NotNull Block block, @NotNull Player primerEntity) {
        super(block);
        this.primerEntity = primerEntity;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
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

    public Player getPrimerEntity() {
        return primerEntity;
    }
}
