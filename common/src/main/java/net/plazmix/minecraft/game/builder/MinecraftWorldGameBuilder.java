package net.plazmix.minecraft.game.builder;

import com.google.common.collect.Queues;
import net.plazmix.minecraft.game.MinecraftWorldGame;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.mode.WorldGame;

import java.util.Queue;

public class MinecraftWorldGameBuilder implements GameBuilder<WorldGame> {

    private final Queue<GameState> queue = Queues.newArrayDeque();
    private String name;

    @Override
    public GameBuilder<WorldGame> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GameBuilder<WorldGame> addState(GameState gameState) {
        queue.add(gameState);
        return this;
    }

    @Override
    public WorldGame build() {
        return new MinecraftWorldGame(name, () -> new MinecraftGameStateController(Queues.newArrayDeque(queue)));
    }
}
