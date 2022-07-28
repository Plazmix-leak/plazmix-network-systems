package net.plazmix.network.server;

import net.plazmix.network.NetworkEntity;
import net.plazmix.network.user.User;

import java.util.Collection;

public interface Server extends NetworkEntity {

    String getPingMessage();

    ServerVersion getVersion();

    ServerType getType();

    int getMaxOnline();

    int getOnline();

    Collection<User> getConnectedUsers();

    void disconnect(User user);

    void stop(String reason);

    void stop();
}
