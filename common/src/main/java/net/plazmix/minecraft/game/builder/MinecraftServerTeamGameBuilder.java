package net.plazmix.minecraft.game.builder;

import com.google.common.collect.Queues;
import net.plazmix.minecraft.game.MinecraftServerTeamGame;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.mode.team.ServerTeamGame;

import java.util.Queue;

public class MinecraftServerTeamGameBuilder implements GameBuilder<ServerTeamGame> {

    private final Queue<GameState> queue = Queues.newArrayDeque();
    private String name;

    @Override
    public GameBuilder<ServerTeamGame> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GameBuilder<ServerTeamGame> addState(GameState gameState) {
        queue.add(gameState);
        return this;
    }

    @Override
    public ServerTeamGame build() {
        return new MinecraftServerTeamGame(name, new MinecraftGameStateController(queue));
    }
}
