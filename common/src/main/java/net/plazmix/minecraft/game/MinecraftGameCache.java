package net.plazmix.minecraft.game;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MinecraftGameCache implements GameCache {

    @Getter
    private final Map<String, Object> map = Maps.newHashMap();

    @Override
    public void set(@NonNull String id, Object value) {
        map.put(id, value);
    }

    @Override
    public <T> T get(@NonNull String id) {
        return (T) map.get(id);
    }

    @Override
    public <T> T get(@NonNull String id, Class<T> valueType) {
        return (T) map.get(id);
    }

    @Override
    public <T> T getOrDefault(@NonNull String id, Supplier<T> def) {
        if (!map.containsKey(id)) {

            if (def == null) {
                return null;
            }

            return def.get();
        }

        return get(id);
    }

    @Override
    public <T> List<T> getList(@NonNull String id, @NonNull Class<T> generic) {
        return get(id, List.class);
    }

    @Override
    public List<String> getStringList(@NonNull String id) {
        return getList(id, String.class);
    }

    @Override
    public String getString(@NonNull String id) {
        return get(id, String.class);
    }

    @Override
    public int getInt(@NonNull String id) {
        return getOrDefault(id, () -> 0);
    }

    @Override
    public double getDouble(@NonNull String id) {
        return getOrDefault(id, () -> 0);
    }

    @Override
    public long getLong(@NonNull String id) {
        return getOrDefault(id, () -> 0);
    }

    @Override
    public <T> T compute(@NonNull String id, Supplier<T> def) {
        if (!map.containsKey(id.toLowerCase())) {

            if (def == null) {
                return null;
            }

            T defValue = def.get();
            set(id, defValue);

            return defValue;
        }

        return get(id);
    }

    @Override
    public void add(@NonNull String id, int valueToAdd) {
        set(id, getOrDefault(id, () -> 0) + valueToAdd);
    }

    @Override
    public void take(@NonNull String id, int valueToRemove) {
        set(id, getOrDefault(id, () -> 0) - valueToRemove);
    }

    @Override
    public void increment(@NonNull String id) {
        add(id, 1);
    }

    @Override
    public void decrement(@NonNull String id) {
        take(id, 1);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(@NonNull String id) {
        return map.containsKey(id);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

}
