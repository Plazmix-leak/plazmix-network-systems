package net.plazmix.minecraft.game.logic;

import net.plazmix.util.time.ticker.TimeTicker;

import java.util.UUID;

public interface GameState {

    String getName();

    GameLogic getLogic();

    <T extends TimeTicker> T addTimeTicker(T timeTicker);

    <T extends TimeTicker> T getTimeTicker(UUID uuid, Class<T> tickerClass);

    TimeTicker getTimeTicker(UUID uuid);

    <T extends TimeTicker> T removeTimeTicker(T timeTicker);

    TimeTicker removeTimeTickerById(UUID uuid);
}
