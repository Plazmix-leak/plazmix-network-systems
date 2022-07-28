package net.plazmix.minecraft.game.session;

import lombok.Data;
import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.mode.ServerGame;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
public class MinecraftServerGameSession implements GameSession {

    private final ServerGame serverGame;
    private final String sessionId;
    private final AtomicBoolean valid = new AtomicBoolean(false);

    public MinecraftServerGameSession(ServerGame serverGame, String sessionId) {
        this.serverGame = serverGame;
        this.sessionId = sessionId;
    }

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
    public GameStateController getStateController() {
        return serverGame.getStateController();
    }

    @Override
    public GameCache getCache() {
        return serverGame.getCache();
    }

    @Override
    public Collection<GamePlayer> getPlayers() {
        return serverGame.getPlayers();
    }

    @Override
    public Optional<GamePlayer> getPlayerById(UUID uuid) {
        return serverGame.getPlayerById(uuid);
    }

    @Override
    public Optional<GamePlayer> getPlayerByName(String name) {
        return serverGame.getPlayerByName(name);
    }

    @Override
    public Result<Void> addPlayer(GamePlayer gamePlayer) {
        return serverGame.addPlayer(gamePlayer);
    }

    @Override
    public Result<Void> removePlayer(GamePlayer gamePlayer) {
        return serverGame.removePlayer(gamePlayer);
    }
}
