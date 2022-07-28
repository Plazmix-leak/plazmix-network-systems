package net.plazmix.game;

import com.google.common.collect.Maps;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.builder.paper.PaperGameLogicBuilder;
import net.plazmix.minecraft.game.logic.MinecraftGameLogic;
import net.plazmix.minecraft.game.logic.builder.MinecraftGameLogicBuilder;
import net.plazmix.minecraft.game.logic.paper.PaperGameLogic;
import net.plazmix.minecraft.game.logic.paper.event.DirectEventLogic;
import net.plazmix.minecraft.game.logic.paper.event.EventLogic;
import net.plazmix.minecraft.game.logic.paper.event.EventLogicMapper;
import net.plazmix.minecraft.game.logic.paper.event.GameEvent;
import net.plazmix.minecraft.game.player.GamePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerEvent;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MinecraftPaperGameLogicBuilder<T extends GameSession> implements PaperGameLogicBuilder<T> {

    private final MinecraftGameLogicBuilder<T> builder;
    private final Map<Class<? extends Event>, DirectEventLogic<T, ? extends Event>> directEventMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, DirectEventLogic<T, ? extends PlayerEvent>> directGamePlayerEventMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, DirectEventLogic<T, ? extends BlockEvent>> directGameBlockEventMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerBlock<T>>> playerBlockLogicMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerInventory<T>>> playerInventoryLogicMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerInventoryAction<T>>> playerInventoryActionLogicMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerItem<T>>> playerItemLogicMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerLocation<T>>> playerLocationLogicMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerEntity<T>>> playerEntityLogicMap = Maps.newHashMap();
    private final Map<Class<? extends Event>, Boolean> eventCancelMap = Maps.newHashMap();
    private boolean defaultValue = false;

    public MinecraftPaperGameLogicBuilder(MinecraftGameLogicBuilder<T> builder) {
        this.builder = builder;
    }

    @Override
    public MinecraftPaperGameLogicBuilder<T> onStart(Consumer<T> consumer) {
        builder.onStart(consumer);
        return this;
    }

    @Override
    public MinecraftPaperGameLogicBuilder<T> onShutdown(Consumer<T> consumer) {
        builder.onShutdown(consumer);
        return this;
    }

    @Override
    public MinecraftPaperGameLogicBuilder<T> allowPlayerAdd() {
        builder.allowPlayerAdd();
        return this;
    }

    @Override
    public MinecraftPaperGameLogicBuilder<T> disallowPlayerAdd() {
        builder.disallowPlayerAdd();
        return this;
    }

    @Override
    public MinecraftPaperGameLogicBuilder<T> postPlayerAdd(BiConsumer<GamePlayer, T> consumer) {
        builder.postPlayerAdd(consumer);
        return this;
    }

    @Override
    public MinecraftPaperGameLogicBuilder<T> postPlayerRemove(BiConsumer<GamePlayer, T> consumer) {
        builder.postPlayerRemove(consumer);
        return this;
    }

    @Override
    public <E extends Event> PaperGameLogicBuilder<T> handleEventDirectly(Class<E> eventClass, EventPriority priority, BiConsumer<T, E> consumer) {
        directEventMap.put(eventClass, new DirectEventLogic<>(eventClass, priority, false, consumer));
        return this;
    }

    @Override
    public <E extends PlayerEvent> PaperGameLogicBuilder<T> handleGamePlayerEventDirectly(Class<E> eventClass, EventPriority priority, BiConsumer<T, E> consumer) {
        directGamePlayerEventMap.put(eventClass, new DirectEventLogic<>(eventClass, priority, false, consumer));
        return this;
    }

    @Override
    public <E extends BlockEvent> PaperGameLogicBuilder<T> handleGameBlockEventDirectly(Class<E> eventClass, EventPriority priority, BiConsumer<T, E> consumer) {
        directGameBlockEventMap.put(eventClass, new DirectEventLogic<>(eventClass, priority, false, consumer));
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> addPlayerBlockEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerBlock<T>> predicate) {
        playerBlockLogicMap.put(mapper.getEventClass(), EventLogic.of(mapper, predicate));
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> addPlayerInventoryEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerInventory<T>> predicate) {
        playerInventoryLogicMap.put(mapper.getEventClass(), EventLogic.of(mapper, predicate));
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> addPlayerInventoryActionEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerInventoryAction<T>> predicate) {
        playerInventoryActionLogicMap.put(mapper.getEventClass(), EventLogic.of(mapper, predicate));
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> addPlayerItemEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerItem<T>> predicate) {
        playerItemLogicMap.put(mapper.getEventClass(), EventLogic.of(mapper, predicate));
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> addPlayerLocationEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerLocation<T>> predicate) {
        playerLocationLogicMap.put(mapper.getEventClass(), EventLogic.of(mapper, predicate));
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> addPlayerEntityEvent(EventLogicMapper<? super T, ? extends Event> mapper, Predicate<GameEvent.PlayerEntity<T>> predicate) {
        playerEntityLogicMap.put(mapper.getEventClass(), EventLogic.of(mapper, predicate));
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> cancel(EventLogicMapper<? super T, ? extends Event> mapper) {
        eventCancelMap.put(mapper.getEventClass(), true);
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> allow(EventLogicMapper<? super T, ? extends Event> mapper) {
        eventCancelMap.put(mapper.getEventClass(), false);
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> cancelAll() {
        this.defaultValue = true;
        return this;
    }

    @Override
    public PaperGameLogicBuilder<T> allowAll() {
        this.defaultValue = false;
        return this;
    }

    @Override
    public PaperGameLogic<T> build() {
        MinecraftGameLogic<T> gameLogic = builder.build();
        return new MinecraftPaperGameLogic(
                gameLogic.getStartConsumer().andThen(session -> {
                    MinecraftPaperGameLogic<GameSession> logic = (MinecraftPaperGameLogic) session.getStateController().getCurrentState().getLogic();
                    logic.getDirectEventMap().values()
                            .forEach(directEventLogic -> directEventLogic.register(session));
                }),
                gameLogic.getShutdownConsumer().andThen(session -> {
                    MinecraftPaperGameLogic<GameSession> logic = (MinecraftPaperGameLogic) session.getStateController().getCurrentState().getLogic();
                    logic.getDirectEventMap().values()
                            .forEach(DirectEventLogic::unregister);
                }),
                gameLogic.getPostPlayerAddConsumer(),
                gameLogic.getPostPlayerRemoveConsumer(),
                gameLogic.isPlayerAddAllowed(),
                directEventMap,
                directGamePlayerEventMap,
                directGameBlockEventMap,
                playerBlockLogicMap,
                playerInventoryLogicMap,
                playerInventoryActionLogicMap,
                playerItemLogicMap,
                playerLocationLogicMap,
                playerEntityLogicMap,
                eventCancelMap,
                defaultValue
        );
    }
}
