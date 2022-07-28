package net.plazmix.minecraft.game.builder;

import com.google.common.collect.Queues;
import net.plazmix.minecraft.game.MinecraftPolygonTeamGame;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.minecraft.game.logic.MinecraftGameStateController;
import net.plazmix.minecraft.game.mode.team.PolygonTeamGame;

import java.util.Queue;

public class MinecraftPolygonTeamGameBuilder implements GameBuilder<PolygonTeamGame> {

    private final Queue<GameState> queue = Queues.newArrayDeque();
    private String name;

    @Override
    public GameBuilder<PolygonTeamGame> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GameBuilder<PolygonTeamGame> addState(GameState gameState) {
        queue.add(gameState);
        return this;
    }

    @Override
    public PolygonTeamGame build() {
        return new MinecraftPolygonTeamGame(name, () -> new MinecraftGameStateController(Queues.newArrayDeque(queue)));
    }
}
