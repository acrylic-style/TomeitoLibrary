package xyz.acrylicstyle.tomeito_api.events.entity;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WitherSkullBlockBreakEvent extends EntityEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false;
    protected float yield;
    @NotNull protected final Location location;
    @NotNull protected final List<Block> blocks;
    @NotNull protected final WitherSkull witherSkull;

    public WitherSkullBlockBreakEvent(@NotNull Location location, @NotNull List<Block> blocks, @NotNull WitherSkull witherSkull, float yield) {
        super(witherSkull);
        this.location = location;
        this.blocks = blocks;
        this.witherSkull = witherSkull;
        this.yield = yield;
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

    @NotNull
    public Location getLocation() {
        return location;
    }

    @NotNull
    public WitherSkull getWitherSkull() {
        return witherSkull;
    }

    @NotNull
    public List<Block> getBlocks() {
        return blocks;
    }

    public float getYield() {
        return yield;
    }

    public void setYield(float yield) {
        this.yield = yield;
    }
}
