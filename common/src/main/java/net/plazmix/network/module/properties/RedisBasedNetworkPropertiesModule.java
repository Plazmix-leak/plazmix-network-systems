package net.plazmix.network.module.properties;

import net.plazmix.network.module.NetworkPropertiesModule;
import net.plazmix.network.module.RedisModule;
import net.plazmix.network.module.configuration.ModuleConfiguration;

public class RedisBasedNetworkPropertiesModule implements NetworkPropertiesModule {

    private static final String KEY_BASE = "/NETWORK/PROPERTIES/";

    private final RedisModule<?> module;

    public RedisBasedNetworkPropertiesModule(RedisModule module) {
        this.module = module;
    }

    @Override
    public void configure(ModuleConfiguration configuration) {}

    @Override
    public void invalidate() {}

    @Override
    public String get(String key) {
        return module.getStringByKey(KEY_BASE + key).orElse("");
    }
}
