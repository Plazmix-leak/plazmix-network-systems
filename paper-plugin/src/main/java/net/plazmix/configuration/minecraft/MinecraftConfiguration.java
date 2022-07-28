package net.plazmix.configuration.minecraft;

import lombok.Getter;
import net.plazmix.config.ConfigFile;

@Getter
public class MinecraftConfiguration {

    private boolean enabled;

    public MinecraftConfiguration(ConfigFile<?> configFile) {
        this.enabled = configFile.getBoolean("minecraft.enabled");
    }
}
