package net.plazmix.minecraft.game.builder;

import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.logic.GameLogic;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.util.function.Builder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface GameLogicBuilder<S extends GameSession, L extends GameLogic, B extends GameLogicBuilder<S, L, B>> extends Builder<L> {

    B onStart(Consumer<S> consumer);

    B onShutdown(Consumer<S> consumer);

    B allowPlayerAdd();

    B disallowPlayerAdd();

    B postPlayerAdd(BiConsumer<GamePlayer, S> consumer);

    B postPlayerRemove(BiConsumer<GamePlayer, S> consumer);
}
