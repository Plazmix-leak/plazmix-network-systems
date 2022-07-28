package net.plazmix.minecraft.game.builder;

import net.plazmix.minecraft.game.logic.GameLogic;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.util.function.Builder;
import net.plazmix.util.time.ticker.TimeTicker;

public interface GameStateBuilder extends Builder<GameState> {

    GameStateBuilder withName(String name);

    GameStateBuilder withLogic(GameLogic gameLogic);

    <T extends TimeTicker> GameStateBuilder withTimeTicker(T timeTicker);
}
