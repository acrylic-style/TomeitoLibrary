package xyz.acrylicstyle.tomeito_api.events.block;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class TNTPrimeEvent extends BlockEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    protected int fuseTicks = 80;
    protected int fireTicks = 0;
    protected float yield = -1F;
    @Nullable protected Vector velocity = null;

    public TNTPrimeEvent(Block theBlock) {
        super(theBlock);
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

    /**
     * Set TNT Fuse ticks.<br />
     * Will be ignored if cancelled.
     * @param fuseTicks Ticks before TNT explodes.
     */
    public void setFuseTicks(int fuseTicks) {
        this.fuseTicks = fuseTicks;
    }

    /**
     * Get TNT Fuse ticks.<br />
     * Will be ignored if cancelled.
     * @return Ticks before TNT explodes.
     */
    public int getFuseTicks() {
        return this.fuseTicks;
    }

    /**
     * Set TNT Fire ticks. Setting to -1 will causes using a default value.<br />
     * Will be ignored if cancelled.
     */
    public void setFireTicks(int fireTicks) {
        this.fireTicks = fireTicks;
    }

    /**
     * Get TNT Fire ticks. -1 means it will causes using a default value.<br />
     * Will be ignored if cancelled.
     */
    public int getFireTicks() {
        return this.fireTicks;
    }

    /**
     * Set TNT yield (explosion radius). Setting to -1 will causes using a default value.<br />
     * Will be ignored if cancelled.
     * @param yield Explosion radius
     */
    public void setYield(float yield) {
        this.yield = yield;
    }

    /**
     * Set TNT yield (explosion radius). Setting to -1 will causes using a default value.<br />
     * Will be ignored if cancelled.
     * @return Explosion radius
     */
    public float getYield() {
        return this.yield;
    }

    /**
     * Set TNT velocity. Setting to null will causes using a default value.<br />
     * Will be ignored if cancelled.
     * @param velocity Velocity
     */
    public void setVelocity(@Nullable Vector velocity) {
        this.velocity = velocity;
    }

    /**
     * Set TNT velocity. Null means it will causes using a default value.<br />
     * Will be ignored if cancelled.
     * @return Velocity
     */
    @Nullable
    public Vector getVelocity() {
        return this.velocity;
    }
}
