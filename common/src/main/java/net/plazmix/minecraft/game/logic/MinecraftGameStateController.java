package net.plazmix.minecraft.game.logic;

import com.google.common.base.Preconditions;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.util.Result;

import java.util.Queue;

public class MinecraftGameStateController implements GameStateController {

    private final Queue<GameState> queue;
    private boolean initialized;
    private GameSession currentSession;

    public MinecraftGameStateController(Queue<GameState> queue) {
        this.queue = queue;
    }

    public void setCurrentSession(GameSession currentSession) {
        this.currentSession = currentSession;
    }

    @Override
    public GameSession getCurrentSession() {
        return currentSession;
    }

    @Override
    public Queue<GameState> getQueue() {
        return queue;
    }

    @Override
    public GameState getCurrentState() {
        return queue.element();
    }

    @Override
    public Result<Void> nextState() {
        Preconditions.checkState(getCurrentSession() != null, "Session is null!");
        if (initialized) {
            getCurrentState().getLogic().getShutdownConsumer().accept(getCurrentSession());
            GameState gameState = queue.poll();
            if (gameState == null)
                return new Result<>(Result.Status.FAILURE, "Next state is not found!");
        }
        getCurrentState().getLogic().getStartConsumer().accept(getCurrentSession());
        initialized = true;
        return new Result<>(Result.Status.SUCCESS);
    }
}
