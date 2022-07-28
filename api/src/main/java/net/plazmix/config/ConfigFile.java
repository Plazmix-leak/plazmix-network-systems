package net.plazmix.config;

import java.util.Collection;
import java.util.List;

public interface ConfigFile<T> {

    String getName();

    T unwrap();

    boolean exists();

    ConfigFile<T> create();

    ConfigFile<T> load();

    ConfigFile<T> save();

    void delete();

    Object getObject(String path);

    Object getObject(String path, Object def);

    List<String> getStringList(String path);

    String getString(String path);

    String getString(String path, String def);

    long getLong(String path);

    long getLong(String path, long def);

    int getInt(String path);

    int getInt(String path, int def);

    boolean getBoolean(String path);

    boolean getBoolean(String path, boolean def);

    Collection<String> getKeys(String path);
}
