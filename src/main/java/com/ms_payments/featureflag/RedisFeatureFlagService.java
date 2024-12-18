package com.ms_payments.featureflag;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisFeatureFlagService implements FeatureFlag {

    private Jedis redisClient;
    private String redisNamespace;

    public RedisFeatureFlagService(String redisHost, int redisPort, String namespace) {
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
