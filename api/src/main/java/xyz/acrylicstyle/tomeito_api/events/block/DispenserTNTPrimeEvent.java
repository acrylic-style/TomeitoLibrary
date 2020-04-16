package xyz.acrylicstyle.tomeito_api.events.block;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when TNT block is about to turn into {@link org.bukkit.entity.TNTPrimed} using dispenser block.
 * <br />
 * Cancelling it will also causes cancelling the BlockDispenseEvent.
 */
public class DispenserTNTPrimeEvent extends BlockEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;

    public DispenserTNTPrimeEvent(@NotNull Block block) {
        super(block);
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
}
