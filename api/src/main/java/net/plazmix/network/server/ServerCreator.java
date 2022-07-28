package net.plazmix.network.server;

import java.util.UUID;

public interface ServerCreator {

    ServerCreator withUniqueId(UUID uuid);

    ServerCreator withExactName(String name);

    ServerCreator withPrefixName(String name);

    ServerCreator withMaxPlayers(int maxPlayers);

    ServerCreator withVersion(ServerVersion version);

    ServerCreator withDefaultPingMessage(String message);
}
