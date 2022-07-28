package net.plazmix.network.server;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Data;
import net.plazmix.network.user.User;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Data
@Builder
public class MinecraftServer implements Server {

    private final UUID uniqueId;
    private final String name;
    private final InetAddress address;
    private final Instant creationTime;
    private final AtomicBoolean aliveServer = new AtomicBoolean(false);
    private final AtomicReference<String> currentPingMessage = new AtomicReference<>(null);
    private final ServerVersion version;
    private final ServerType type;
    private final int maxOnline;
    private final AtomicInteger currentOnline = new AtomicInteger();

    @Override
    public Instant getLifetime() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return aliveServer.get();
    }

    @Override
    public String getPingMessage() {
        return currentPingMessage.get();
    }

    @Override
    public int getOnline() {
        return currentOnline.get();
    }

    @Override
    public Collection<User> getConnectedUsers() {
        return Sets.newHashSet();
    }

    @Override
    public void disconnect(User user) {

    }

    @Override
    public void stop(String reason) {

    }

    @Override
    public void stop() {

    }
}
