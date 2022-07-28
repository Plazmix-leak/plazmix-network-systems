package net.plazmix;

import net.plazmix.configuration.PaperPluginConfiguration;
import net.plazmix.configuration.network.NetworkConfiguration;
import net.plazmix.configuration.network.module.source.PropertiesSource;
import net.plazmix.module.YamlLocalizationModule;
import net.plazmix.network.PlazmixNetwork;
import net.plazmix.network.module.GameMapModule;
import net.plazmix.network.module.LocalizationModule;
import net.plazmix.network.module.NetworkPropertiesModule;
import net.plazmix.network.module.RedisModule;
import net.plazmix.network.module.properties.RedisBasedNetworkPropertiesModule;
import net.plazmix.network.module.redis.PlazmixRedisModule;
import net.plazmix.module.YamlGameMapModule;
import net.plazmix.network.server.MinecraftServer;
import net.plazmix.network.server.Server;
import net.plazmix.network.server.ServerType;
import net.plazmix.network.user.MinecraftUser;
import net.plazmix.network.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class PaperNetwork extends PlazmixNetwork {

    public PaperNetwork(Plugin plugin, PlazmixNetworkPlugin plazmixNetworkPlugin, NetworkConfiguration configuration) {
        super(load(plugin, plazmixNetworkPlugin, configuration.getServerName(), configuration.getServerType()));

        if (configuration.getModules().getRedis().isEnabled()) {
            PlazmixRedisModule redisModule = new PlazmixRedisModule();
            registerModule(RedisModule.class, redisModule);

            if (configuration.getModules().getProperties().isEnabled() && configuration.getModules().getProperties().getSource() == PropertiesSource.REDIS)
                registerModule(NetworkPropertiesModule.class, new RedisBasedNetworkPropertiesModule(redisModule));
        }

        if (configuration.getModules().getProperties().isEnabled()) {
            if (configuration.getModules().getProperties().getSource() == PropertiesSource.PROPERTIES_FILE);
            else if (configuration.getModules().getProperties().getSource() == PropertiesSource.RUNTIME);
        }

        if (configuration.getModules().getLocalization().isEnabled()) {
            switch (configuration.getModules().getLocalization().getSource()) {
                case YAML_FILE:
                    plugin.saveResource("localization.yml", false);
                    registerModule(LocalizationModule.class, new YamlLocalizationModule(plugin.getDataFolder()));
                    break;
            }
        }

        if (configuration.getModules().getGameMap().isEnabled()) {
            switch (configuration.getModules().getGameMap().getSource()) {
                case YAML_FILE:
                    plugin.saveResource("maps.yml", false);
                    registerModule(GameMapModule.class, new YamlGameMapModule(plugin.getDataFolder()));
                    break;
            }
        }
    }

    @Override
    public Optional<User> getUserById(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null || !player.isOnline())
            return Optional.empty();
        MinecraftUser minecraftUser = MinecraftUser.builder()
                .uniqueId(uuid)
                .name(player.getName())
                .address(player.getAddress().getAddress())
                .build();
        minecraftUser.getCurrentServer().set(getPluginServer());
        minecraftUser.getAlivePlayer().set(true);
        minecraftUser.getCurrentLocale().set(Locale.forLanguageTag(player.spigot().getLocale()));
        return Optional.of(minecraftUser);
    }

    private static Server load(Plugin plugin, PlazmixNetworkPlugin plazmixPlugin, String name, ServerType type) {
        try {
            return MinecraftServer.builder()
                    .uniqueId(plazmixPlugin.getUniqueId())
                    .name(name)
                    .maxOnline(plugin.getServer().getMaxPlayers())
                    .creationTime(Instant.now())
                    .version(plazmixPlugin.getServerVersion())
                    .type(type)
                    .address(InetAddress.getByName(plugin.getServer().getIp()))
                    .build();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Failed to load current server address!");
    }
}
