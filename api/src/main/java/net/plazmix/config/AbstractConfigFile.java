package net.plazmix.config;

import com.google.common.base.Preconditions;
import net.plazmix.PlazmixAPI;

import java.io.File;
import java.io.IOException;

public abstract class AbstractConfigFile<T> implements ConfigFile<T> {

    protected final File file;
    protected T config;

    protected AbstractConfigFile(File folder, String fileName) {
        this.file = new File(folder + File.separator + fileName);
    }

    protected AbstractConfigFile(File file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public T unwrap() {
        Preconditions.checkState(config != null, "Configuration is not loaded!");
        return config;
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public ConfigFile<T> create() {
        if (!exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                PlazmixAPI.getLogger().warning("Unable to create this file! Details: " + e.getMessage());
            }
        return this;
    }

    @Override
    public void delete() {
        if (exists())
            file.delete();
    }
}
