package net.plazmix.minecraft.game.logic.builder;

import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.builder.GameLogicBuilder;
import net.plazmix.minecraft.game.logic.MinecraftGameLogic;
import net.plazmix.minecraft.game.player.GamePlayer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MinecraftGameLogicBuilder<S extends GameSession> implements GameLogicBuilder<S, MinecraftGameLogic<S>, MinecraftGameLogicBuilder<S>> {

    private Consumer<S> startConsumer = s -> {};
    private Consumer<S> shutdownConsumer = s -> {};
    private BiConsumer<GamePlayer, S> playerAddConsumer = (p, s) -> {};
    private BiConsumer<GamePlayer, S> playerRemoveConsumer = (p, s) -> {};
    private boolean playerAddAllowed;

    @Override
    public MinecraftGameLogicBuilder<S> onStart(Consumer<S> consumer) {
        this.startConsumer = consumer;
        return this;
    }

    @Override
    public MinecraftGameLogicBuilder<S> onShutdown(Consumer<S> consumer) {
        this.shutdownConsumer = consumer;
        return this;
    }

    @Override
    public MinecraftGameLogicBuilder<S> allowPlayerAdd() {
        this.playerAddAllowed = true;
        return this;
    }

    @Override
    public MinecraftGameLogicBuilder<S> disallowPlayerAdd() {
        this.playerAddAllowed = false;
        return this;
    }

    @Override
    public MinecraftGameLogicBuilder<S> postPlayerAdd(BiConsumer<GamePlayer, S> consumer) {
        this.playerAddConsumer = consumer;
        return this;
    }

    @Override
    public MinecraftGameLogicBuilder<S> postPlayerRemove(BiConsumer<GamePlayer, S> consumer) {
        this.playerRemoveConsumer = consumer;
        return this;
    }

    @Override
    public MinecraftGameLogic build() {
        return new MinecraftGameLogic(startConsumer, shutdownConsumer, playerAddConsumer, playerRemoveConsumer, playerAddAllowed);
    }
}
