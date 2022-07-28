package net.plazmix;

import net.plazmix.network.server.ServerVersion;

import java.util.UUID;

public interface PlazmixNetworkPlugin {

    UUID getUniqueId();

    String getName();

    String getVersion();

    ServerVersion getServerVersion();
}
