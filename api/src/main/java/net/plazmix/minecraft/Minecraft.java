package net.plazmix.minecraft;

import net.plazmix.minecraft.game.Game;
import net.plazmix.minecraft.game.GameRegistry;
import net.plazmix.minecraft.game.builder.GameBuilder;
import net.plazmix.minecraft.game.builder.GameStateBuilder;
import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.hologram.HologramBuilder;
import net.plazmix.minecraft.platform.Platform;
import net.plazmix.minecraft.tag.TagFactory;
import net.plazmix.util.time.ticker.TimeTicker;

import java.util.Optional;
import java.util.UUID;

public interface Minecraft {

    <T extends Platform> Optional<T> getPlatform(Class<T> clazz);

    HologramBuilder newHologramBuilder();

    <T extends Game> GameBuilder<T> newGameBuilder(Class<T> gameClass);

    GameStateBuilder newGameStateBuilder();

    Team newTeam(String id);

    GameRegistry getGameRegistry();

    TagFactory getTagFactory();

    <T extends TimeTicker> T runTimeTicker(T timeTicker);

    <T extends TimeTicker> T runAsyncTimeTicker(T timeTicker);

    TimeTicker getTimeTickerById(UUID uuid);

    <T extends TimeTicker> T getTimeTickerById(UUID uuid, Class<T> tickerClass);

    <T extends TimeTicker> T terminateTimeTicker(T timeTicker);

    <T extends TimeTicker> T terminateTimeTickerById(UUID uuid);
}
