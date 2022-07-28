package net.plazmix.minecraft.game.logic.paper;

import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.logic.GameLogic;
import net.plazmix.minecraft.game.logic.paper.event.DirectEventLogic;
import net.plazmix.minecraft.game.logic.paper.event.EventLogic;
import net.plazmix.minecraft.game.logic.paper.event.GameEvent;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerEvent;

import java.util.Optional;

public interface PaperGameLogic<T extends GameSession> extends GameLogic<T> {

    <E extends Event> Optional<DirectEventLogic<T, E>> getDirectEvent(Class<E> eventClass);

    <E extends PlayerEvent> Optional<DirectEventLogic<T, E>> getDirectGamePlayerEvent(Class<E> eventClass);

    <E extends BlockEvent> Optional<DirectEventLogic<T, E>> getDirectGameBlockEvent(Class<E> eventClass);

    EventLogic<? extends Event, GameEvent.PlayerBlock<T>> getBlockLogic(Class<? extends Event> eventClass);

    EventLogic<? extends Event, GameEvent.PlayerInventory<T>> getInventoryLogic(Class<? extends Event> eventClass);

    EventLogic<? extends Event, GameEvent.PlayerInventoryAction<T>> getInventoryActionLogic(Class<? extends Event> eventClass);

    EventLogic<? extends Event, GameEvent.PlayerItem<T>> getItemLogic(Class<? extends Event> eventClass);

    EventLogic<? extends Event, GameEvent.PlayerLocation<T>> getLocationLogic(Class<? extends Event> eventClass);

    EventLogic<? extends Event, GameEvent.PlayerEntity<T>> getEntityLogic(Class<? extends Event> eventClass);

    boolean isCancelled(Class<? extends Event> eventClass);
}
