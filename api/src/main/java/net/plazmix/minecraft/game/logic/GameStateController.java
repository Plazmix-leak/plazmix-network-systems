package net.plazmix.minecraft.game.logic;

import net.plazmix.minecraft.game.GameSession;
import net.plazmix.util.Result;

import java.util.Queue;

public interface GameStateController {

    GameSession getCurrentSession();

    Queue<GameState> getQueue();

    GameState getCurrentState();

    Result<Void> nextState();
}
