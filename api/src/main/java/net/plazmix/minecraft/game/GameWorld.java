package net.plazmix.minecraft.game;

import net.plazmix.minecraft.game.player.GamePlayer;

import java.util.Collection;

public interface GameWorld {

    String getName();

    GameCache getCache();

    Collection<GamePlayer> getPlayers();
}
