package net.plazmix.game;

import lombok.Getter;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.logic.MinecraftGameLogic;
import net.plazmix.minecraft.game.logic.paper.PaperGameLogic;
import net.plazmix.minecraft.game.logic.paper.event.DirectEventLogic;
import net.plazmix.minecraft.game.logic.paper.event.EventLogic;
import net.plazmix.minecraft.game.logic.paper.event.GameEvent;
import net.plazmix.minecraft.game.player.GamePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerEvent;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
public class MinecraftPaperGameLogic<T extends GameSession> extends MinecraftGameLogic<T> implements PaperGameLogic<T> {

    private final Map<Class<? extends Event>, DirectEventLogic<T, ? extends Event>> directEventMap;
    private final Map<Class<? extends Event>, DirectEventLogic<T, ? extends PlayerEvent>> directGamePlayerEventMap;
    private final Map<Class<? extends Event>, DirectEventLogic<T, ? extends BlockEvent>> directGameBlockEventMap;
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerBlock<T>>> playerBlockLogicMap;
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerInventory<T>>> playerInventoryLogicMap;
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerInventoryAction<T>>> playerInventoryActionLogicMap;
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerItem<T>>> playerItemLogicMap;
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerLocation<T>>> playerLocationLogicMap;
    private final Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerEntity<T>>> playerEntityLogicMap;
    private final Map<Class<? extends Event>, Boolean> eventCancelMap;
    private final boolean defaultValue;

    public MinecraftPaperGameLogic(Consumer<T> startConsumer, Consumer<T> shutdownConsumer, BiConsumer<GamePlayer, T> postPlayerAddConsumer, BiConsumer<GamePlayer, T> postPlayerRemoveConsumer, boolean playerAddAllowed,
                                   Map<Class<? extends Event>, DirectEventLogic<T, ? extends Event>> directEventMap, Map<Class<? extends Event>, DirectEventLogic<T, ? extends PlayerEvent>> directGamePlayerEventMap, Map<Class<? extends Event>, DirectEventLogic<T, ? extends BlockEvent>> directGameBlockEventMap,
                                   Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerBlock<T>>> playerBlockLogicMap, Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerInventory<T>>> playerInventoryLogicMap, Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerInventoryAction<T>>> playerInventoryActionLogicMap, Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerItem<T>>> playerItemLogicMap, Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerLocation<T>>> playerLocationLogicMap, Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerEntity<T>>> playerEntityLogicMap,
                                   Map<Class<? extends Event>, Boolean> eventCancelMap, boolean defaultValue) {
        super(startConsumer, shutdownConsumer, postPlayerAddConsumer, postPlayerRemoveConsumer, playerAddAllowed);
        this.directEventMap = directEventMap;
        this.directGamePlayerEventMap = directGamePlayerEventMap;
        this.directGameBlockEventMap = directGameBlockEventMap;
        this.playerBlockLogicMap = playerBlockLogicMap;
        this.playerInventoryLogicMap = playerInventoryLogicMap;
        this.playerInventoryActionLogicMap = playerInventoryActionLogicMap;
        this.playerItemLogicMap = playerItemLogicMap;
        this.playerLocationLogicMap = playerLocationLogicMap;
        this.playerEntityLogicMap = playerEntityLogicMap;
        this.eventCancelMap = eventCancelMap;
        this.defaultValue = defaultValue;
    }

    @Override
    public <E extends Event> Optional<DirectEventLogic<T, E>> getDirectEvent(Class<E> eventClass) {
        DirectEventLogic<T, E> eventLogic = (DirectEventLogic<T, E>) directEventMap.get(eventClass);
        return Optional.ofNullable(eventLogic);
    }

    @Override
    public <E extends PlayerEvent> Optional<DirectEventLogic<T, E>> getDirectGamePlayerEvent(Class<E> eventClass) {
        DirectEventLogic<T, E> eventLogic = (DirectEventLogic<T, E>) directGamePlayerEventMap.get(eventClass);
        return Optional.ofNullable(eventLogic);
    }

    @Override
    public <E extends BlockEvent> Optional<DirectEventLogic<T, E>> getDirectGameBlockEvent(Class<E> eventClass) {
        DirectEventLogic<T, E> eventLogic = (DirectEventLogic<T, E>) directGameBlockEventMap.get(eventClass);
        return Optional.ofNullable(eventLogic);
    }

    @Override
    public EventLogic<? extends Event, GameEvent.PlayerBlock<T>> getBlockLogic(Class<? extends Event> eventClass) {
        return playerBlockLogicMap.get(eventClass);
    }

    @Override
    public EventLogic<? extends Event, GameEvent.PlayerInventory<T>> getInventoryLogic(Class<? extends Event> eventClass) {
        return playerInventoryLogicMap.get(eventClass);
    }

    @Override
    public EventLogic<? extends Event, GameEvent.PlayerInventoryAction<T>> getInventoryActionLogic(Class<? extends Event> eventClass) {
        return playerInventoryActionLogicMap.get(eventClass);
    }

    @Override
    public EventLogic<? extends Event, GameEvent.PlayerItem<T>> getItemLogic(Class<? extends Event> eventClass) {
        return playerItemLogicMap.get(eventClass);
    }

    @Override
    public EventLogic<? extends Event, GameEvent.PlayerLocation<T>> getLocationLogic(Class<? extends Event> eventClass) {
        return playerLocationLogicMap.get(eventClass);
    }

    @Override
    public EventLogic<? extends Event, GameEvent.PlayerEntity<T>> getEntityLogic(Class<? extends Event> eventClass) {
        return playerEntityLogicMap.get(eventClass);
    }

    @Override
    public boolean isCancelled(Class<? extends Event> eventClass) {
        return eventCancelMap.getOrDefault(eventClass, defaultValue);
    }
}
