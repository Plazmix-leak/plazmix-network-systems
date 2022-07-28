package net.plazmix.minecraft.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import lombok.Data;
import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.mode.ServerGame;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.player.MinecraftGamePlayer;
import net.plazmix.network.module.LocalizationModule;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
public abstract class AbstractServerGame<T extends GameSession> implements ServerGame<T> {

    private final String name;
    private final GameStateController stateController;
    private final GameCache cache = new MinecraftGameCache();
    private final Collection<GamePlayer> players = Sets.newHashSet();
    private final AtomicBoolean valid = new AtomicBoolean(false);

    @Override
    public boolean isActive() {
        return valid.get();
    }

    @Override
    public Result<Void> setActive(boolean active) {
        if (!active)
            getCache().clear();
        return new Result<>(valid.getAndSet(active) != active ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    protected T session;

    @Override
    public Optional<T> getGameSession(String id) {
        if (getSessionId().equals(id))
            return Optional.of(session);
        return Optional.empty();
    }

    @Override
    public String getSessionId() {
        Preconditions.checkState(session != null, "Session cannot be null!");
        return session.getSessionId();
    }

    @Override
    public Optional<GamePlayer> getPlayerById(UUID uuid) {
        return players.stream().filter(player -> player.getUniqueId().equals(uuid))
                .limit(1).findFirst();
    }

    @Override
    public Optional<GamePlayer> getPlayerByName(String name) {
        return players.stream().filter(player -> player.getName().equalsIgnoreCase(name))
                .limit(1).findFirst();
    }

    @Override
    public Result<Void> addPlayer(GamePlayer gamePlayer) {
        if (!session.getStateController().getCurrentState().getLogic().isPlayerAddAllowed())
            return new Result<>(Result.Status.FAILURE, LocalizationModule.SystemTextKey.GAMESTATE_JOIN_DISALLOWED.name());
        if (session.getCache().getInt(GameSession.GAME_SESSION_PLAYER_LIMIT) > 0 && session.getCache().getInt(GAME_SESSION_PLAYER_LIMIT) <= getPlayers().size())
            return new Result<>(Result.Status.FAILURE, LocalizationModule.SystemTextKey.GAME_IS_FULL.name());
        Result<Void> result = new Result<>(players.add(gamePlayer) ? Result.Status.SUCCESS : Result.Status.FAILURE);
        return result.onSuccess(() -> {
            session.addPlayer(gamePlayer);
            if (gamePlayer instanceof MinecraftGamePlayer) {
                MinecraftGamePlayer minecraftGamePlayer = (MinecraftGamePlayer) gamePlayer;
                minecraftGamePlayer.setCurrentGame(this);
            }
            getStateController().getCurrentState().getLogic().getPostPlayerAddConsumer().accept(gamePlayer, this);
        });
    }

    @Override
    public Result<Void> removePlayer(GamePlayer gamePlayer) {
        Result<Void> result = new Result<>(players.remove(gamePlayer) ? Result.Status.SUCCESS : Result.Status.FAILURE);
        return result.onSuccess(() -> {
            session.removePlayer(gamePlayer);
            if (gamePlayer instanceof MinecraftGamePlayer) {
                MinecraftGamePlayer minecraftGamePlayer = (MinecraftGamePlayer) gamePlayer;
                minecraftGamePlayer.setCurrentGame(null);
            }
            getStateController().getCurrentState().getLogic().getPostPlayerRemoveConsumer().accept(gamePlayer, this);
        });
    }
}
