package net.plazmix.minecraft.game;

import java.util.Optional;

public interface Game<T extends GameSession> {

    String getName();

    Optional<T> getGameSession(String id);
}
