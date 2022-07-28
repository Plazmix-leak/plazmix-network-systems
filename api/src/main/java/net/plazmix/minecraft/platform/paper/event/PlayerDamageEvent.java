package net.plazmix.minecraft.platform.paper.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerEvent;

public class PlayerDamageEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final EntityDamageEvent event;

    public PlayerDamageEvent(Player who, EntityDamageEvent event) {
        super(who);
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public EntityDamageEvent getEvent() {
        return event;
    }

    public double getOriginalDamage(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return event.getOriginalDamage(type);
    }

    public void setDamage(EntityDamageEvent.DamageModifier type, double damage) throws IllegalArgumentException, UnsupportedOperationException {
        event.setDamage(type, damage);
    }

    public double getDamage(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return event.getDamage(type);
    }

    public boolean isApplicable(EntityDamageEvent.DamageModifier type) throws IllegalArgumentException {
        return event.isApplicable(type);
    }

    public double getDamage() {
        return event.getDamage();
    }

    public void setDamage(double damage) {
        event.setDamage(damage);
    }

    public double getFinalDamage() {
        return event.getFinalDamage();
    }

    @Deprecated
    public int _INVALID_getDamage() {
        return event._INVALID_getDamage();
    }

    @Deprecated
    public void _INVALID_setDamage(int damage) {
        event._INVALID_setDamage(damage);
    }

    public EntityDamageEvent.DamageCause getCause() {
        return event.getCause();
    }

    @Override
    public boolean isCancelled() {
        return event.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        event.setCancelled(cancel);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
