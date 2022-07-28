package net.plazmix.minecraft.game.mode;

import net.plazmix.minecraft.game.Game;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.util.Result;

import java.util.function.Consumer;

public interface ServerGame<T extends GameSession> extends Game<T>, GameSession {

    Result<T> run();

    Result<T> run(Consumer<T> consumer);

    Result<T> run(String id);

    Result<T> run(String id, Consumer<T> consumer);
}
