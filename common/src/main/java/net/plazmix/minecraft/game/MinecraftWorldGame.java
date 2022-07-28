package net.plazmix.minecraft.game;

import net.plazmix.PlazmixAPI;
import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.session.MinecraftWorldGameSession;
import net.plazmix.util.Result;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MinecraftWorldGame extends AbstractWorldGame<MinecraftWorldGameSession> {

    private final Supplier<GameStateController> gameStateControllerSupplier;

    public MinecraftWorldGame(String name, Supplier<GameStateController> gameStateControllerSupplier) {
        super(name);
        this.gameStateControllerSupplier = gameStateControllerSupplier;
    }

    @Override
    public Result<MinecraftWorldGameSession> run(GameWorld world) {
        return run(world, String.format("%s_%s_session", getName(), world.getName()));
    }

    @Override
    public Result<MinecraftWorldGameSession> run(GameWorld world, Consumer<MinecraftWorldGameSession> consumer) {
        return run(world, String.format("%s_%s_session", getName(), world.getName()), consumer);
    }

    @Override
    public Result<MinecraftWorldGameSession> run(GameWorld world, String id) {
        getGames().stream().filter(session -> !session.isActive()).collect(Collectors.toSet())
                .forEach(session -> getSessionMap().remove(session.getSessionId()));
        if (getSessionMap().containsKey(id))
            return new Result(Result.Status.FAILURE, String.format("Session with id %s is already created!", id));
        if (getGames().stream().filter(game -> game.getWorld().equals(world)).count() > 0)
            return new Result(Result.Status.FAILURE, String.format("Session is already created for world %s!", world.getName()));
        MinecraftWorldGameSession session = new MinecraftWorldGameSession(id, world, gameStateControllerSupplier.get());
        session.setActive(true);
        getSessionMap().put(id, session);
        ((MinecraftGameStateController) session.getStateController()).setCurrentSession(session);
        session.getStateController().nextState();
        return new Result(Result.Status.SUCCESS, session);
    }

    @Override
    public Result<MinecraftWorldGameSession> run(GameWorld world, String id, Consumer<MinecraftWorldGameSession> consumer) {
        getGames().stream().filter(session -> !session.isActive()).collect(Collectors.toSet())
                .forEach(session -> getSessionMap().remove(session.getSessionId()));
        if (getSessionMap().containsKey(id))
            return new Result(Result.Status.FAILURE, String.format("Session with id %s is already created!", id));
        if (getGames().stream().filter(game -> game.getWorld().equals(world)).count() > 0)
            return new Result(Result.Status.FAILURE, String.format("Session is already created for world %s!", world.getName()));
        MinecraftWorldGameSession session = new MinecraftWorldGameSession(id, world, gameStateControllerSupplier.get());
        session.setActive(true);
        getSessionMap().put(id, session);
        ((MinecraftGameStateController) session.getStateController()).setCurrentSession(session);
        session.getStateController().nextState();
        return new Result(Result.Status.SUCCESS, session);
    }

    @Override
    public Optional<MinecraftWorldGameSession> getSessionByWorld(GameWorld world) {
        return getGames().stream().filter(session -> session.getWorld().equals(world))
                .findFirst();
    }
}
