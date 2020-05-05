package xyz.acrylicstyle.tomeito_api.events.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class EntityDamageByPlayerEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    protected boolean cancelled = false;
    protected final Entity entity;
    protected double damage;
    protected final double finalDamage;

    public EntityDamageByPlayerEvent(Player damager, Entity entity, double damage, double finalDamage) {
        super(damager);
        this.entity = entity;
        this.damage = damage;
        this.finalDamage = finalDamage;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Get player who damaged entity.
     * @return Player who damaged entity
     */
    public Player getDamager() {
        return this.player;
    }

    /**
     * Get entity who damaged by player.
     * @return Entity who damaged by player
     */
    public Entity getEntity() {
        return this.entity;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public final double getFinalDamage() {
        return finalDamage;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
