package net.plazmix.configuration.logger;

import lombok.Getter;
import net.plazmix.config.ConfigFile;

@Getter
public class LoggerConfiguration {

    private boolean enabled;

    public LoggerConfiguration(ConfigFile<?> configFile) {
        this.enabled = configFile.getBoolean("logger.enabled");
    }
}
