package net.plazmix.minecraft.game;

import com.google.common.base.Preconditions;
import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.session.MinecraftServerGameSession;
import net.plazmix.util.Result;

import java.util.function.Consumer;

public class MinecraftServerGame extends AbstractServerGame<MinecraftServerGameSession> {

    public MinecraftServerGame(String name, GameStateController stateController) {
        super(name, stateController);
    }

    @Override
    public Result run() {
        return run(String.format("%s_session", getName()));
    }

    @Override
    public Result run(Consumer<MinecraftServerGameSession> consumer) {
        return run(String.format("%s_session", getName()), consumer);
    }

    @Override
    public Result run(String id) {
        if (getSession() != null && !getSession().isActive())
            setSession(null);
        Preconditions.checkState(getSession() == null, "Game session is already created!");
        MinecraftServerGameSession session = new MinecraftServerGameSession(this, id);
        session.setActive(true);
        setSession(session);
        ((MinecraftGameStateController) session.getStateController()).setCurrentSession(session);
        session.getStateController().nextState();
        return new Result(getSession() != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    @Override
    public Result run(String id, Consumer<MinecraftServerGameSession> consumer) {
        if (getSession() != null && !getSession().isActive())
            setSession(null);
        Preconditions.checkState(getSession() == null, "Game session is already created!");
        MinecraftServerGameSession session = new MinecraftServerGameSession(this, id);
        session.setActive(true);
        setSession(session);
        consumer.accept(getSession());
        ((MinecraftGameStateController) session.getStateController()).setCurrentSession(session);
        session.getStateController().nextState();
        return new Result(getSession() != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }
}
