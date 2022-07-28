package net.plazmix.network.module.redis;

import lombok.Builder;
import lombok.Data;
import net.plazmix.network.module.configuration.ModuleConfiguration;
import redis.clients.jedis.JedisPoolConfig;

@Data
@Builder
public class RedisConfiguration implements ModuleConfiguration {

    private final JedisPoolConfig poolConfig;
    private final String host, password;
    private final int port, timeout;
}
