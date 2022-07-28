package net.plazmix.minecraft.game.builder;

import com.google.common.collect.Queues;
import net.plazmix.minecraft.game.MinecraftWorldTeamGame;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.mode.team.WorldTeamGame;

import java.util.Queue;

public class MinecraftWorldTeamGameBuilder implements GameBuilder<WorldTeamGame> {

    private final Queue<GameState> queue = Queues.newArrayDeque();
    private String name;

    @Override
    public GameBuilder<WorldTeamGame> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GameBuilder<WorldTeamGame> addState(GameState gameState) {
        queue.add(gameState);
        return this;
    }

    @Override
    public WorldTeamGame build() {
        return new MinecraftWorldTeamGame(name, () -> new MinecraftGameStateController(Queues.newArrayDeque(queue)));
    }
}
