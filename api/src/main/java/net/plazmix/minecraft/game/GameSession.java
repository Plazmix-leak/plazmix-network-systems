package net.plazmix.minecraft.game;

import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface GameSession {

    String GAME_SESSION_TITLE = "GAME_SESSION_TITLE";
    String GAME_SESSION_PLAYER_LIMIT = "GAME_SESSION_PLAYER_LIMIT";

    GameStateController getStateController();

    boolean isActive();

    Result<Void> setActive(boolean active);

    String getSessionId();

    GameCache getCache();

    Collection<GamePlayer> getPlayers();

    Optional<GamePlayer> getPlayerById(UUID uuid);

    Optional<GamePlayer> getPlayerByName(String name);

    Result<Void> addPlayer(GamePlayer gamePlayer);

    Result<Void> removePlayer(GamePlayer gamePlayer);
}
