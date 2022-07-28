package net.plazmix.configuration.network.module;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RedisConfiguration {

    private boolean enabled;
}
