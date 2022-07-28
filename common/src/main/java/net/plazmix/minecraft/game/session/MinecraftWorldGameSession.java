package net.plazmix.minecraft.game.session;

import com.google.common.collect.Sets;
import lombok.Data;
import net.plazmix.minecraft.game.*;
import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.player.MinecraftGamePlayer;
import net.plazmix.network.module.LocalizationModule;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
public class MinecraftWorldGameSession implements WorldGameSession {

    private final String sessionId;
    private final GameWorld world;
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
        if (!getStateController().getCurrentState().getLogic().isPlayerAddAllowed())
            return new Result<>(Result.Status.FAILURE, LocalizationModule.SystemTextKey.GAMESTATE_JOIN_DISALLOWED.name());
        if (getCache().getInt(GameSession.GAME_SESSION_PLAYER_LIMIT) > 0 && getCache().getInt(GAME_SESSION_PLAYER_LIMIT) <= getPlayers().size())
            return new Result<>(Result.Status.FAILURE, LocalizationModule.SystemTextKey.GAME_IS_FULL.name());
        Result<Void> result = new Result<>(players.add(gamePlayer) ? Result.Status.SUCCESS : Result.Status.FAILURE);
        return result.onSuccess(() -> {
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
            if (gamePlayer instanceof MinecraftGamePlayer) {
                MinecraftGamePlayer minecraftGamePlayer = (MinecraftGamePlayer) gamePlayer;
                minecraftGamePlayer.setCurrentGame(null);
            }
            getStateController().getCurrentState().getLogic().getPostPlayerRemoveConsumer().accept(gamePlayer, this);
        });
    }
}
