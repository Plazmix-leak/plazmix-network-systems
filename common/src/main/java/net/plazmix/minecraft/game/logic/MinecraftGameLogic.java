package net.plazmix.minecraft.game.logic;

import lombok.Data;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.player.GamePlayer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
public class MinecraftGameLogic<T extends GameSession> implements GameLogic<T> {

    private final Consumer<T> startConsumer, shutdownConsumer;
    private final BiConsumer<GamePlayer, T> postPlayerAddConsumer, postPlayerRemoveConsumer;
    private final boolean playerAddAllowed;
}
