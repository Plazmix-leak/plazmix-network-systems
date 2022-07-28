package net.plazmix.minecraft.game.player;

import net.plazmix.PlazmixAPI;
import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.MinecraftGameCache;
import net.plazmix.network.server.Server;
import net.plazmix.network.user.User;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class MinecraftGamePlayer implements GamePlayer {

    private final User user;
    private final GameCache cache = new MinecraftGameCache();
    private GameSession currentGame;

    public MinecraftGamePlayer(User user) {
        this.user = user;
    }

    public void setCurrentGame(GameSession currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public Optional<GameSession> getCurrentGame() {
        return Optional.ofNullable(currentGame);
    }

    @Override
    public GameCache getCache() {
        return cache;
    }

    @Override
    public UUID getUniqueId() {
        return user.getUniqueId();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public InetAddress getAddress() {
        return user.getAddress();
    }

    @Override
    public Instant getCreationTime() {
        return user.getCreationTime();
    }

    @Override
    public Instant getLifetime() {
        return user.getLifetime();
    }

    @Override
    public boolean isAlive() {
        return user.isAlive();
    }

    @Override
    public Server getServer() {
        return user.getServer();
    }

    @Override
    public boolean isOnline() {
        return user.isOnline();
    }

    @Override
    public Locale getLocale() {
        return user.getLocale();
    }

    @Override
    public boolean isPlaying() {
        return user != null && user.isOnline() && user.getServer().equals(PlazmixAPI.getNetwork().getPluginServer());
    }
}
