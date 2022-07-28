package net.plazmix.network;

import java.net.InetAddress;
import java.time.Instant;
import java.util.UUID;

public interface NetworkEntity {

    UUID getUniqueId();

    String getName();

    InetAddress getAddress();

    Instant getCreationTime();

    Instant getLifetime();

    boolean isAlive();
}
