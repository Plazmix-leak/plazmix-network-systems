package net.plazmix.minecraft.game.mode;

import net.plazmix.minecraft.game.Game;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.util.geometry.polygon.Polygon;
import net.plazmix.util.Result;

import java.util.Collection;
import java.util.function.Consumer;

public interface PolygonGame<T extends GameSession> extends Game<T> {

    Collection<T> getGames();

    Result<T> run(Polygon polygon);

    Result<T> run(Polygon polygon, Consumer<T> consumer);

    Result<T> run(Polygon polygon, String id);

    Result<T> run(Polygon polygon, String id, Consumer<T> consumer);
}
