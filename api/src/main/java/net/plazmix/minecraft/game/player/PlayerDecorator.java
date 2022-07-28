package net.plazmix.minecraft.game.player;

import net.plazmix.minecraft.game.GameCache;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.network.server.Server;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class PlayerDecorator implements GamePlayer {

    protected final GamePlayer player;

    public PlayerDecorator(GamePlayer player) {
        this.player = player;
    }

    @Override
    public Optional<GameSession> getCurrentGame() {
        return player.getCurrentGame();
    }

    @Override
    public GameCache getCache() {
        return player.getCache();
    }

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public InetAddress getAddress() {
        return player.getAddress();
    }

    @Override
    public Instant getCreationTime() {
        return player.getCreationTime();
    }

    @Override
    public Instant getLifetime() {
        return player.getLifetime();
    }

    @Override
    public boolean isAlive() {
        return player.isAlive();
    }

    @Override
    public Server getServer() {
        return player.getServer();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public Locale getLocale() {
        return player.getLocale();
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }
}
