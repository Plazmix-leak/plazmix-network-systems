package net.plazmix.network.module;

import net.plazmix.network.module.configuration.ModuleConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface NetworkPropertiesModule extends NetworkModule<ModuleConfiguration> {

    String get(String key);

    default String get(String key, Supplier<String> defaultSupplier) {
        return getOptional(key).orElseGet(defaultSupplier);
    }

    default Optional<String> getOptional(String key) {
        return Optional.ofNullable(get(key))
                .filter(value -> !value.isEmpty());
    }

    default List<String> getList(String key) {
        return getOptional(key)
                .map(value -> Stream.of(value.split(","))
                        .map(String::trim)
                        .filter(str -> !str.isEmpty())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
