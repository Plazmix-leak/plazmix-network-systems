package net.plazmix.minecraft.game.logic.builder;

import com.google.common.collect.Maps;
import net.plazmix.minecraft.game.builder.GameStateBuilder;
import net.plazmix.minecraft.game.logic.GameLogic;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.minecraft.game.logic.MinecraftGameState;
import net.plazmix.util.time.ticker.TimeTicker;

import java.util.Map;
import java.util.UUID;

public class MinecraftGameStateBuilder implements GameStateBuilder {

    private final Map<UUID, ? super TimeTicker> timeTickers = Maps.newHashMap();
    private String name;
    private GameLogic gameLogic;

    @Override
    public GameStateBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GameStateBuilder withLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        return this;
    }

    @Override
    public <T extends TimeTicker> GameStateBuilder withTimeTicker(T timeTicker) {
        timeTickers.put(timeTicker.getUniqueId(), timeTicker);
        return this;
    }

    @Override
    public GameState build() {
        return new MinecraftGameState(name, gameLogic, timeTickers);
    }
}
