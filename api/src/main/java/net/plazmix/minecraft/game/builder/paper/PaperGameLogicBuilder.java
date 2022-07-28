package net.plazmix.minecraft.game.builder.paper;

import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.builder.GameLogicBuilder;
import net.plazmix.minecraft.game.logic.paper.PaperGameLogic;
import net.plazmix.minecraft.game.logic.paper.event.EventLogicMapper;
import net.plazmix.minecraft.game.logic.paper.event.GameEvent;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerEvent;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public interface PaperGameLogicBuilder<T extends GameSession> extends GameLogicBuilder<T, PaperGameLogic<T>, PaperGameLogicBuilder<T>> {

    <E extends Event> PaperGameLogicBuilder<T> handleEventDirectly(Class<E> eventClass, EventPriority priority, BiConsumer<T, E> consumer);

    default <E extends Event> PaperGameLogicBuilder<T> handleEventDirectly(Class<E> eventClass, BiConsumer<T, E> consumer) {
        return handleEventDirectly(eventClass, consumer);
    }

    <E extends PlayerEvent> PaperGameLogicBuilder<T> handleGamePlayerEventDirectly(Class<E> eventClass, EventPriority priority, BiConsumer<T, E> consumer);

    default <E extends PlayerEvent> PaperGameLogicBuilder<T> handleGamePlayerEventDirectly(Class<E> eventClass, BiConsumer<T, E> consumer) {
        return handleGamePlayerEventDirectly(eventClass, EventPriority.NORMAL, consumer);
    }

    <E extends BlockEvent> PaperGameLogicBuilder<T> handleGameBlockEventDirectly(Class<E> eventClass, EventPriority priority, BiConsumer<T, E> consumer);

    default <E extends BlockEvent> PaperGameLogicBuilder<T> handleGameBlockEventDirectly(Class<E> eventClass, BiConsumer<T, E> consumer) {
        return handleGameBlockEventDirectly(eventClass, EventPriority.NORMAL, consumer);
    }

    PaperGameLogicBuilder<T> addPlayerBlockEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerBlock<T>> predicate);

    PaperGameLogicBuilder<T> addPlayerInventoryEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerInventory<T>> predicate);

    PaperGameLogicBuilder<T> addPlayerInventoryActionEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerInventoryAction<T>> predicate);

    PaperGameLogicBuilder<T> addPlayerItemEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerItem<T>> predicate);

    PaperGameLogicBuilder<T> addPlayerLocationEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerLocation<T>> predicate);

    PaperGameLogicBuilder<T> addPlayerEntityEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerEntity<T>> predicate);

    PaperGameLogicBuilder<T> cancel(EventLogicMapper<? super T, ? extends Event> mapper);

    PaperGameLogicBuilder<T> allow(EventLogicMapper<? super T, ? extends Event> mapper);

    PaperGameLogicBuilder<T> cancelAll();

    PaperGameLogicBuilder<T> allowAll();
}
