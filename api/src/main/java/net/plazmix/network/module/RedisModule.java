package net.plazmix.network.module;

import net.plazmix.network.module.configuration.ModuleConfiguration;
import net.plazmix.util.Result;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface RedisModule<T extends ModuleConfiguration> extends NetworkModule<T> {

    Result<String> putString(String key, String value);

    Result<String> replaceString(String key, String value);

    Optional<String> getStringByKey(String key);

    Map<String, String> getStringsByMatch(String match);

    Result<Boolean> removeByKey(String key);

    Result<Long> removeByMatch(String match);

    boolean contains(String key);

    FastOperations fast();

    Maps maps();

    Sets sets();

    interface FastOperations {

        void fastPut(String key, String value);

        void fastRemove(String key);

        void fastRemoveBatch(String[] keys);
    }

    interface Maps {

        boolean isMap(String key);

        Map<String, String> getMap(String key);

        String get(String key, String mapKey);

        Result<Void> put(String key, String mapKey, String value);

        Result<Void> putAll(String key, Map<String, String> map);

        Result<Void> removeKeys(String key, String... keys);
    }

    interface Sets {

        boolean isSet(String key);

        Set<String> getSet(String key);

        Result<Void> add(String key, String... strings);

        Result<Void> remove(String key, String... strings);
    }
}
