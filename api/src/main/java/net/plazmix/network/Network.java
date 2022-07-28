package net.plazmix.network;

import net.plazmix.PlazmixNetworkPlugin;
import net.plazmix.network.module.NetworkModule;
import net.plazmix.network.security.SecurityKey;
import net.plazmix.network.server.Server;
import net.plazmix.network.server.ServerCreator;
import net.plazmix.network.server.ServerFilter;
import net.plazmix.network.server.ServerType;
import net.plazmix.network.user.User;
import net.plazmix.util.Result;

import java.util.Optional;
import java.util.UUID;

public interface Network {

    String getName();

    ServerCreator getServerCreator();

    Server getPluginServer();

    Optional<Server> getCurrentServer(User user);

    Optional<Server> getServerById(UUID uuid);

    Optional<Server> getServerByName(String name);

    Optional<User> getUserById(UUID uuid);

    Optional<User> getUserByName(String name);

    Result<Void> redirect(User user, Server server);

    Result<Server> redirect(User user, ServerType serverType, ServerFilter... filters);

    Result<Void> disconnect(User user);

    Result<Server> createServer(ServerCreator serverCreator);

    Result<Void> deleteServerById(UUID uuid);

    Result<Void> deleteServerByName(String name);

    Result<Void> deleteServer(Server server);

    <T extends NetworkModule> Result<T> registerModule(Class<T> moduleClass, T networkModule);

    <T extends NetworkModule> boolean isRegistered(Class<T> moduleClass);

    <T extends NetworkModule> Result<NetworkModule> unregisterModule(Class<T> moduleClass);

    Result<Integer> unregisterAll();

    <T extends NetworkModule> Optional<T> getModule(Class<T> moduleClass);

    <T extends NetworkModule> Optional<T> getModule(Class<T> moduleClass, SecurityKey<PlazmixNetworkPlugin> securityKey);

    <T extends NetworkModule> boolean isSecured(Class<T> moduleClass);
}
