package net.plazmix.network.user;

import lombok.Builder;
import lombok.Data;
import net.plazmix.network.server.Server;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Data
@Builder
public class MinecraftUser implements User {

    private final UUID uniqueId;
    private final String name;
    private final InetAddress address;
    private final Instant creationTime;
    private final AtomicBoolean alivePlayer = new AtomicBoolean(false);
    private final AtomicReference<Server> currentServer = new AtomicReference<>(null);
    private final AtomicReference<Locale> currentLocale = new AtomicReference<>(null);

    @Override
    public Instant getLifetime() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return alivePlayer.get();
    }

    @Override
    public Server getServer() {
        return currentServer.get();
    }

    @Override
    public Locale getLocale() {
        return currentLocale.get();
    }
}
