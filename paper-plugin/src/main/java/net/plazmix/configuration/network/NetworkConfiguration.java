package net.plazmix.configuration.network;

import lombok.Getter;
import net.plazmix.config.ConfigFile;
import net.plazmix.config.paper.YamlConfigFile;
import net.plazmix.configuration.network.module.*;
import net.plazmix.configuration.network.module.source.GameMapSource;
import net.plazmix.configuration.network.module.source.LocalizationSource;
import net.plazmix.configuration.network.module.source.PropertiesSource;
import net.plazmix.network.server.ServerType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class NetworkConfiguration {

    private boolean enabled;
    private String serverName;
    private ServerType serverType;
    private Modules modules;

    public NetworkConfiguration(ConfigFile<?> configFile) {
        this.enabled = configFile.getBoolean("network.enabled");
        this.serverName = configFile.getString("network.server-name");
        this.serverType = ServerType.valueOf(configFile.getString("network.server-type").toUpperCase());

        this.modules = Modules.builder()
                .redis(RedisConfiguration.builder()
                        .enabled(false)
                        .build())
                .properties(PropertiesConfiguration.builder()
                        .enabled(configFile.getBoolean("network.modules.properties.enabled"))
                        .source(PropertiesSource.valueOf(configFile.getString("network.modules.properties.source").toUpperCase()))
                        .build())
                .localization(LocalizationConfiguration.builder()
                        .enabled(configFile.getBoolean("network.modules.localization.enabled"))
                        .source(LocalizationSource.valueOf(configFile.getString("network.modules.localization.source").toUpperCase()))
                        .build())
                .gameMap(GameMapConfiguration.builder()
                        .enabled(configFile.getBoolean("network.modules.gamemap.enabled"))
                        .source(GameMapSource.valueOf(configFile.getString("network.modules.gamemap.source").toUpperCase()))
                        .build())
                .build();
    }
}
