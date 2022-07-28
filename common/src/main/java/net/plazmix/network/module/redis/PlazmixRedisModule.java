package net.plazmix.network.module.redis;

import com.google.common.base.Preconditions;
import net.plazmix.network.module.RedisModule;
import net.plazmix.util.Result;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.*;

public class PlazmixRedisModule implements RedisModule<RedisConfiguration>, RedisModule.FastOperations, RedisModule.Maps, RedisModule.Sets {

    private JedisPool jedisPool;
    private boolean valid;

    @Override
    public void configure(RedisConfiguration configuration) {
        Preconditions.checkState(!valid, "RedisModule is already configured and running!");
        this.jedisPool = new JedisPool(configuration.getPoolConfig(), configuration.getHost(), configuration.getPort(), configuration.getTimeout(), configuration.getPassword());
        this.valid = true;
    }

    @Override
    public void invalidate() {
        if (valid) {
            jedisPool.close();
            jedisPool = null;
            valid = false;
        }
    }

    @Override
    public Result<String> putString(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            Optional<String> previousString = getStringByKey(key);
            jedis.set(key, value);
            return previousString.map(s -> new Result<>(Result.Status.SUCCESS, null, s))
                    .orElseGet(() -> new Result<>(Result.Status.SUCCESS));
        } catch (Throwable throwable) {
            return new Result<>(Result.Status.FAILURE, throwable.getMessage());
        }
    }

    @Override
    public Result<String> replaceString(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            Optional<String> previousString = getStringByKey(key);
            if (previousString.isPresent()) {
                jedis.set(key, value);
                return new Result<>(Result.Status.SUCCESS, null, previousString.get());
            }
            return new Result<>(Result.Status.FAILURE, "No previous value present");
        } catch (Throwable throwable) {
            return new Result<>(Result.Status.FAILURE, throwable.getMessage());
        }
    }

    @Override
    public Optional<String> getStringByKey(String key) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.get(key);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Map<String, String> getStringsByMatch(String match) {
        Map<String, String> result = new HashMap<>();
        Collection<String> matchingKeys = new HashSet<>();
        ScanParams params = new ScanParams();
        params.match(match);
        try (Jedis jedis = jedisPool.getResource()) {
            String nextCursor = "0";

            do {
                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
                List<String> keys = scanResult.getResult();
                nextCursor = scanResult.getCursor();

                matchingKeys.addAll(keys);
            } while (!nextCursor.equals("0"));

            Pipeline pipeline = jedis.pipelined();
            Map<String, Response<String>> responses = new HashMap<>();

            for (String key : matchingKeys)
                responses.put(key, pipeline.get(key));

            pipeline.sync();
            for (Map.Entry<String, Response<String>> entry : responses.entrySet())
                result.put(entry.getKey(), entry.getValue().get());
        } catch (Throwable ignored) {}
        return result;
    }

    @Override
    public Result<Boolean> removeByKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        } catch (Throwable throwable) {
            return new Result<>(Result.Status.FAILURE, throwable.getMessage(), false);
        }
        return new Result<>(Result.Status.SUCCESS, true);
    }

    @Override
    public Result<Long> removeByMatch(String match) {
        Collection<String> matchingKeys = new HashSet<>();
        ScanParams params = new ScanParams();
        params.match(match);
        try (Jedis jedis = jedisPool.getResource()) {
            String nextCursor = "0";

            do {
                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
                List<String> keys = scanResult.getResult();
                nextCursor = scanResult.getCursor();

                matchingKeys.addAll(keys);
            } while (!nextCursor.equals("0"));

            Pipeline pipeline = jedis.pipelined();
            for (String key : matchingKeys)
                pipeline.del(key);
        } catch (Throwable ignored) {}
        return new Result<>(Result.Status.SUCCESS, (long) matchingKeys.size());
    }

    @Override
    public boolean contains(String key) {
        boolean result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.exists(key);
        }
        return result;
    }

    @Override
    public FastOperations fast() {
        return this;
    }

    @Override
    public Maps maps() {
        return this;
    }

    @Override
    public Sets sets() {
        return this;
    }

    @Override
    public void fastPut(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    @Override
    public void fastRemove(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    @Override
    public void fastRemoveBatch(String[] keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(keys);
        }
    }

    @Override
    public boolean isMap(String key) {
        boolean set;
        try (Jedis jedis = jedisPool.getResource()) {
            set = jedis.type(key).equalsIgnoreCase("hash");
        }
        return set;
    }

    @Override
    public Map<String, String> getMap(String key) {
        Map<String, String> map = null;
        try (Jedis jedis = jedisPool.getResource()) {
            map = jedis.hgetAll(key);
        } finally {
            if (map == null)
                map = new HashMap<>();
        }
        return map;
    }

    @Override
    public String get(String key, String mapKey) {
        String str;
        try (Jedis jedis = jedisPool.getResource()) {
            str = jedis.hget(key, mapKey);
        }
        return str;
    }

    @Override
    public Result<Void> put(String key, String mapKey, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, mapKey, value);
        } catch (Throwable throwable) {
            return new Result<>(Result.Status.FAILURE, throwable.getMessage());
        }
        return new Result<>(Result.Status.SUCCESS);
    }

    @Override
    public Result<Void> putAll(String key, Map<String, String> map) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(key, map);
        } catch (Throwable throwable) {
            return new Result<>(Result.Status.FAILURE, throwable.getMessage());
        }
        return new Result<>(Result.Status.SUCCESS);
    }

    @Override
    public Result<Void> removeKeys(String key, String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hdel(key, keys);
        } catch (Throwable throwable) {
            return new Result<>(Result.Status.FAILURE, throwable.getMessage());
        }
        return new Result<>(Result.Status.SUCCESS);
    }

    @Override
    public boolean isSet(String key) {
        boolean set;
        try (Jedis jedis = jedisPool.getResource()) {
            set = jedis.type(key).equalsIgnoreCase("set");
        }
        return set;
    }

    @Override
    public Set<String> getSet(String key) {
        Set<String> set = null;
        try (Jedis jedis = jedisPool.getResource()) {
            set = jedis.smembers(key);
        } finally {
            if (set == null)
                set = new HashSet<>();
        }
        return set;
    }

    @Override
    public Result<Void> add(String key, String... strings) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(key, strings);
        } catch (Throwable throwable) {
            return new Result<>(Result.Status.FAILURE, throwable.getMessage());
        }
        return new Result<>(Result.Status.SUCCESS);
    }

    @Override
    public Result<Void> remove(String key, String... strings) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.srem(key, strings);
        } catch (Throwable throwable) {
            return new Result<>(Result.Status.FAILURE, throwable.getMessage());
        }
        return new Result<>(Result.Status.SUCCESS);
    }
}
