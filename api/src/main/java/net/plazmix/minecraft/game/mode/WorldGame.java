package net.plazmix.minecraft.game.mode;

import net.plazmix.minecraft.game.Game;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.GameWorld;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public interface WorldGame<T extends GameSession> extends Game<T> {

    Collection<T> getGames();

    Optional<T> getSessionByWorld(GameWorld world);

    Result<T> run(GameWorld world);

    Result<T> run(GameWorld world, Consumer<T> consumer);

    Result<T> run(GameWorld world, String id);

    Result<T> run(GameWorld world, String id, Consumer<T> consumer);
}
