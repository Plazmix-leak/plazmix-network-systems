package net.plazmix.minecraft.game;

import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.network.user.User;
import net.plazmix.util.Result;

public interface GameRegistry {

    Result<Void> registerGame(Game game);

    Result<Void> unregisterGame(Game game);

    Result<Void> unregisterGameByName(String name);

    GamePlayer asGamePlayer(User user);
}
