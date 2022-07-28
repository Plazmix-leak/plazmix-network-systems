package net.plazmix.minecraft.game;

import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.session.MinecraftPolygonGameSession;
import net.plazmix.minecraft.util.geometry.polygon.Polygon;
import net.plazmix.util.Result;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MinecraftPolygonGame extends AbstractPolygonGame<MinecraftPolygonGameSession> {

    private final Supplier<GameStateController> gameStateControllerSupplier;

    public MinecraftPolygonGame(String name, Supplier<GameStateController> gameStateControllerSupplier) {
        super(name);
        this.gameStateControllerSupplier = gameStateControllerSupplier;
    }

    @Override
    public Result<MinecraftPolygonGameSession> run(Polygon polygon) {
        return run(polygon, String.format("%s_%s_session", getName(), polygon.getUniqueId().toString()));
    }

    @Override
    public Result<MinecraftPolygonGameSession> run(Polygon polygon, Consumer<MinecraftPolygonGameSession> consumer) {
        return run(polygon, String.format("%s_%s_session", getName(), polygon.getUniqueId().toString()), consumer);
    }

    @Override
    public Result<MinecraftPolygonGameSession> run(Polygon polygon, String id) {
        getGames().stream().filter(session -> !session.isActive()).collect(Collectors.toSet())
                .forEach(session -> getSessionMap().remove(session.getSessionId()));
        if (getSessionMap().containsKey(id))
            return new Result(Result.Status.FAILURE, String.format("Session with id %s is already created!", id));
        if (getGames().stream().filter(game -> game.getPolygon().getUniqueId().equals(polygon.getUniqueId())).count() > 0)
            return new Result(Result.Status.FAILURE, String.format("Session is already created for polygon %s!", polygon.getUniqueId()));
        MinecraftPolygonGameSession session = new MinecraftPolygonGameSession(id, polygon, gameStateControllerSupplier.get());
        session.setActive(true);
        getSessionMap().put(id, session);
        ((MinecraftGameStateController) session.getStateController()).setCurrentSession(session);
        session.getStateController().nextState();
        return new Result(Result.Status.SUCCESS, session);
    }

    @Override
    public Result<MinecraftPolygonGameSession> run(Polygon polygon, String id, Consumer<MinecraftPolygonGameSession> consumer) {
        getGames().stream().filter(session -> !session.isActive()).collect(Collectors.toSet())
                .forEach(session -> getSessionMap().remove(session.getSessionId()));
        if (getSessionMap().containsKey(id))
            return new Result(Result.Status.FAILURE, String.format("Session with id %s is already created!", id));
        if (getGames().stream().filter(game -> game.getPolygon().getUniqueId().equals(polygon.getUniqueId())).count() > 0)
            return new Result(Result.Status.FAILURE, String.format("Session is already created for polygon %s!", polygon.getUniqueId()));
        MinecraftPolygonGameSession session = new MinecraftPolygonGameSession(id, polygon, gameStateControllerSupplier.get());
        getSessionMap().put(id, session);
        ((MinecraftGameStateController) session.getStateController()).setCurrentSession(session);
        session.getStateController().nextState();
        return new Result(Result.Status.SUCCESS, session);
    }
}
