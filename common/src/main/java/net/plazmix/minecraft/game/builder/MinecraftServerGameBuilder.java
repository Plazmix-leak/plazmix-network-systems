package net.plazmix.minecraft.game.builder;

import com.google.common.collect.Queues;
import net.plazmix.minecraft.game.MinecraftServerGame;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.mode.ServerGame;

import java.util.Queue;

public class MinecraftServerGameBuilder implements GameBuilder<ServerGame> {

    private final Queue<GameState> queue = Queues.newArrayDeque();
    private String name;

    @Override
    public GameBuilder<ServerGame> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GameBuilder<ServerGame> addState(GameState gameState) {
        queue.add(gameState);
        return this;
    }

    @Override
    public ServerGame build() {
        return new MinecraftServerGame(name, new MinecraftGameStateController(queue));
    }
}
