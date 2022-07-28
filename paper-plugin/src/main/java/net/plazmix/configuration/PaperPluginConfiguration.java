package net.plazmix.configuration;

import lombok.Getter;
import net.plazmix.config.ConfigFile;
import net.plazmix.config.paper.YamlConfigFile;
import net.plazmix.configuration.logger.LoggerConfiguration;
import net.plazmix.configuration.minecraft.MinecraftConfiguration;
import net.plazmix.configuration.network.NetworkConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class PaperPluginConfiguration extends YamlConfigFile {

    private LoggerConfiguration logger;
    private NetworkConfiguration network;
    private MinecraftConfiguration minecraft;

    public PaperPluginConfiguration(File folder) {
        super(folder, "config");
        load();
    }

    @Override
    public ConfigFile<YamlConfiguration> load() {
        ConfigFile<YamlConfiguration> file = super.load();

        this.logger = new LoggerConfiguration(this);
        this.network = new NetworkConfiguration(this);
        this.minecraft = new MinecraftConfiguration(this);

        return file;
    }
}
