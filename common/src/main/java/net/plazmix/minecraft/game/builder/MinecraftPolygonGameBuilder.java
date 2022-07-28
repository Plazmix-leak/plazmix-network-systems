package net.plazmix.minecraft.game.builder;

import com.google.common.collect.Queues;
import net.plazmix.minecraft.game.MinecraftPolygonGame;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.mode.PolygonGame;

import java.util.Queue;

public class MinecraftPolygonGameBuilder implements GameBuilder<PolygonGame> {

    private final Queue<GameState> queue = Queues.newArrayDeque();
    private String name;

    @Override
    public GameBuilder<PolygonGame> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GameBuilder<PolygonGame> addState(GameState gameState) {
        queue.add(gameState);
        return this;
    }

    @Override
    public PolygonGame build() {
        return new MinecraftPolygonGame(name, () -> new MinecraftGameStateController(Queues.newArrayDeque(queue)));
    }
}
