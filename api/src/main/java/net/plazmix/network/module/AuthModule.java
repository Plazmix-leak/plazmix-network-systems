package net.plazmix.network.module;

import net.plazmix.network.user.AuthenticatedUser;
import net.plazmix.network.user.User;
import net.plazmix.util.Result;

import java.util.Optional;
import java.util.UUID;

public interface AuthModule {

    Optional<AuthenticatedUser> getUserById(UUID uuid);

    Optional<AuthenticatedUser> getUserByName(String name);

    Result<AuthenticatedUser> register(User user, String password);

    Result<AuthenticatedUser> login(User user, String password);

    Result<AuthenticatedUser> logout(User user);

    boolean isRegistered(User user);

    boolean isLoggedIn(User user);
}
