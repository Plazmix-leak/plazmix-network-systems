package net.plazmix.network.module;

import net.plazmix.network.user.User;
import net.plazmix.util.Result;

import java.util.Collection;

public interface FriendModule extends NetworkModule {

    Settings getSettings(User user);

    Collection<User> getFriends(User user);

    Result<Void> sendRequest(User sender, User reciever);

    Result<Void> cancelRequest(User sender, User reciever);

    Result<Void> accept(User user, User friend);

    Result<Void> reject(User sender, User reciever);

    interface Settings {

    }
}
