package net.plazmix.network.module;

import net.plazmix.network.module.configuration.ModuleConfiguration;

public interface NetworkModule<T extends ModuleConfiguration> {

    void configure(T configuration);

    void invalidate();
}
