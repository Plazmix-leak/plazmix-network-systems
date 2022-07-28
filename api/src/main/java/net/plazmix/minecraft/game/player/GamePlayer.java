package net.plazmix.minecraft.game.player;

import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.network.user.User;

import java.util.Optional;

public interface GamePlayer extends User {

    Optional<GameSession> getCurrentGame();

    GameCache getCache();

    boolean isPlaying();
}
