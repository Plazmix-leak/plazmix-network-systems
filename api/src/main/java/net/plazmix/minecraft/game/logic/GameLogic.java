package net.plazmix.minecraft.game.logic;

import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.player.GamePlayer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface GameLogic<T extends GameSession> {

    Consumer<T> getStartConsumer();

    Consumer<T> getShutdownConsumer();

    BiConsumer<GamePlayer, T> getPostPlayerAddConsumer();

    BiConsumer<GamePlayer, T> getPostPlayerRemoveConsumer();

    boolean isPlayerAddAllowed();
}
