package net.plazmix.minecraft.game;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.player.MinecraftGamePlayer;
import net.plazmix.network.user.User;
import net.plazmix.util.Result;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class AbstractGameRegistry implements GameRegistry {

    protected final Map<String, Game> registeredGames = Maps.newHashMap();
    private final Cache<UUID, GamePlayer> gamePlayerCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5L, TimeUnit.MINUTES)
            .build();

    @Override
    public Result<Void> registerGame(Game game) {
        return new Result<>(registeredGames.putIfAbsent(game.getName(), game) == null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    @Override
    public Result<Void> unregisterGame(Game game) {
        return unregisterGameByName(game.getName());
    }

    @Override
    public Result<Void> unregisterGameByName(String name) {
        return new Result<>(registeredGames.remove(name) != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    @Override
    public GamePlayer asGamePlayer(User user) {
        GamePlayer gamePlayer = gamePlayerCache.getIfPresent(user.getUniqueId());
        if (gamePlayer == null) {
            gamePlayer = new MinecraftGamePlayer(user);
            gamePlayerCache.put(user.getUniqueId(), gamePlayer);
        }
        return gamePlayer;
    }
}
