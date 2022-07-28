package net.plazmix.config.paper;

import com.google.common.base.Preconditions;
import net.plazmix.PlazmixAPI;
import net.plazmix.config.AbstractConfigFile;
import net.plazmix.config.ConfigFile;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class YamlConfigFile extends AbstractConfigFile<YamlConfiguration> {

    public YamlConfigFile(File folder, String fileName) {
        super(folder, fileName + ".yml");
    }

    public YamlConfigFile(File file) {
        super(file);
        Preconditions.checkArgument(file.getName().endsWith(".yml"), "File is not in a YAML format!");
    }

    @Override
    public ConfigFile<YamlConfiguration> load() {
        Preconditions.checkState(exists(), "File not exists!");
        if (config == null)
            this.config = YamlConfiguration.loadConfiguration(file);
        else {
            try {
                this.config.load(file);
            } catch (FileNotFoundException e) {
                PlazmixAPI.getLogger().warning("File not found! Details: " + e.getMessage());
            } catch (IOException e) {
                PlazmixAPI.getLogger().severe("Error while loading data from file! Details: " + e.getMessage());
            } catch (InvalidConfigurationException e) {
                PlazmixAPI.getLogger().warning("Unable to load data from file! Details: " + e.getMessage());
            }
        }
        return this;
    }

    @Override
    public ConfigFile<YamlConfiguration> save() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            PlazmixAPI.getLogger().severe("Unable to save data to file! Details: " + e.getMessage());
        }
        return this;
    }

    @Override
    public Object getObject(String path) {
        return this.config.get(path);
    }

    @Override
    public Object getObject(String path, Object def) {
        return this.config.get(path, def);
    }

    @Override
    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    @Override
    public String getString(String path) {
        return this.config.getString(path);
    }

    @Override
    public String getString(String path, String def) {
        return this.config.getString(path, def);
    }

    @Override
    public long getLong(String path) {
        return this.config.getLong(path);
    }

    @Override
    public long getLong(String path, long def) {
        return this.config.getLong(path, def);
    }

    @Override
    public int getInt(String path) {
        return this.config.getInt(path);
    }

    @Override
    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }

    @Override
    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    @Override
    public Collection<String> getKeys(String path) {
        return path.isEmpty() ? config.getKeys(false) : config.getConfigurationSection(path).getKeys(false);
    }
}