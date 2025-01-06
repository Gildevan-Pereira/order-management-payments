package com.ms_payments.featureflag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisFeatureFlagService implements FeatureFlag {

    private final Jedis redisClient;
    private final String redisNamespace;

    public RedisFeatureFlagService(
            @Value("${spring.redis.host}")
            String redisHost,
            @Value("${spring.redis.port}")
            int redisPort,
            @Value("${spring.redis.namespace}")
            String namespace) {
        this.redisClient = new Jedis(redisHost, redisPort);
        this.redisNamespace = namespace;
    }

    private String getRedisKey(String featureName) {
        return redisNamespace + ":" + featureName;
    }

    @Override
    public boolean isFeatureEnabled(String featureName) {
        String redisKey = getRedisKey(featureName);
        String value = redisClient.get(redisKey);

        if (value == null) {
            return false;
        }

        return Boolean.parseBoolean(value);
    }

    @Override
    public void updateFeatureFlag(String featureName, boolean enabled) {
        String redisKey = getRedisKey(featureName);
        redisClient.set(redisKey, String.valueOf(enabled));
    }
}
