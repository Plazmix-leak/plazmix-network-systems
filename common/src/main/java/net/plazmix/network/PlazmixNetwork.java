package net.plazmix.network;

import com.google.common.collect.Maps;
import net.plazmix.PlazmixAPI;
import net.plazmix.PlazmixNetworkPlugin;
import net.plazmix.network.module.NetworkModule;
import net.plazmix.network.security.SecurityKey;
import net.plazmix.network.server.Server;
import net.plazmix.network.server.ServerCreator;
import net.plazmix.network.server.ServerFilter;
import net.plazmix.network.server.ServerType;
import net.plazmix.network.user.User;
import net.plazmix.util.Result;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlazmixNetwork implements Network {

    private final Map<Class<? extends NetworkModule>, NetworkModule> networkModuleMap = Maps.newHashMap();
    private final Server pluginServer;

    public PlazmixNetwork(Server pluginServer) {
        this.pluginServer = pluginServer;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ServerCreator getServerCreator() {
        return null;
    }

    @Override
    public Server getPluginServer() {
        return pluginServer;
    }

    @Override
    public Optional<Server> getCurrentServer(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<Server> getServerById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<Server> getServerByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return Optional.empty();
    }

    @Override
    public Result<Void> redirect(User user, Server server) {
        return null;
    }

    @Override
    public Result<Server> redirect(User user, ServerType serverType, ServerFilter... filters) {
        return null;
    }

    @Override
    public Result<Void> disconnect(User user) {
        return null;
    }

    @Override
    public Result<Server> createServer(ServerCreator serverCreator) {
        return null;
    }

    @Override
    public Result<Void> deleteServerById(UUID uuid) {
        return null;
    }

    @Override
    public Result<Void> deleteServerByName(String name) {
        return null;
    }

    @Override
    public Result<Void> deleteServer(Server server) {
        return null;
    }

    @Override
    public <T extends NetworkModule> Result<T> registerModule(Class<T> moduleClass, T networkModule) {
        Result<T> result = new Result<>(networkModuleMap.putIfAbsent(moduleClass, networkModule) == null ? Result.Status.SUCCESS : Result.Status.FAILURE, networkModule);
        return result.onSuccess(() -> PlazmixAPI.getLogger().info(String.format("%s is loaded successfully!", moduleClass.getSimpleName())));
    }

    @Override
    public <T extends NetworkModule> boolean isRegistered(Class<T> networkModule) {
        return networkModuleMap.containsKey(networkModule.getClass());
    }

    @Override
    public <T extends NetworkModule> Result<NetworkModule> unregisterModule(Class<T> moduleClass) {
        return new Result<>(networkModuleMap.remove(moduleClass) != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
    }

    @Override
    public Result<Integer> unregisterAll() {
        return null;
    }

    @Override
    public <T extends NetworkModule> Optional<T> getModule(Class<T> moduleClass) {
        return Optional.ofNullable((T) networkModuleMap.get(moduleClass));
    }

    @Override
    public <T extends NetworkModule> Optional<T> getModule(Class<T> moduleClass, SecurityKey<PlazmixNetworkPlugin> securityKey) {
        return null;
    }

    @Override
    public <T extends NetworkModule> boolean isSecured(Class<T> moduleClass) {
        return false;
    }
}
