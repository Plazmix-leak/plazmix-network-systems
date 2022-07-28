package net.plazmix.minecraft.game;

import lombok.NonNull;

import java.util.List;
import java.util.function.Supplier;

public interface GameCache {

    void set(@NonNull String id, Object value);

    <T> T get(@NonNull String id);

    <T> T get(@NonNull String id, Class<T> valueType);

    <T> T getOrDefault(@NonNull String id, Supplier<T> def);

    <T> List<T> getList(@NonNull String id, @NonNull Class<T> generic);

    List<String> getStringList(@NonNull String id);

    String getString(@NonNull String id);

    int getInt(@NonNull String id);

    double getDouble(@NonNull String id);

    long getLong(@NonNull String id);

    <T> T compute(@NonNull String id, Supplier<T> def);

    void add(@NonNull String id, int valueToAdd);

    void take(@NonNull String id, int valueToRemove);

    void increment(@NonNull String id);

    void decrement(@NonNull String id);

    void clear();

    boolean contains(@NonNull String id);

    boolean isEmpty();
}
