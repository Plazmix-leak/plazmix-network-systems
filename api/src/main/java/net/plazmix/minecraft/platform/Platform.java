package net.plazmix.minecraft.platform;

import net.plazmix.network.server.ServerVersion;

public interface Platform {

    String getName();

    String getVersion();

    ServerVersion getServerVersion();
}
