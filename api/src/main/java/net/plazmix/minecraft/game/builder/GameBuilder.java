package net.plazmix.minecraft.game.builder;

import net.plazmix.minecraft.game.Game;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.util.function.Builder;

public interface GameBuilder<T extends Game> extends Builder<T> {

    GameBuilder<T> withName(String name);

    GameBuilder<T> addState(GameState gameState);
}
