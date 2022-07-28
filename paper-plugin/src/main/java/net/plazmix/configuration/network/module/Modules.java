package net.plazmix.configuration.network.module;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Modules {

    private RedisConfiguration redis;
    private PropertiesConfiguration properties;
    private LocalizationConfiguration localization;
    private GameMapConfiguration gameMap;
}
