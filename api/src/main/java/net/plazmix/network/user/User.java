package net.plazmix.network.user;

import net.plazmix.network.NetworkEntity;
import net.plazmix.network.server.Server;

import java.util.Locale;

public interface User extends NetworkEntity {

    Server getServer();

    Locale getLocale();

    default boolean isOnline() {
        return isAlive();
    }
}
