package com.testec.spring.boot.elasticcache.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Collection;
import java.util.List;


@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${redis.hostname}")
    private String redisHostName;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.prefix}")
    private String redisPrefix;
    
    @Value("${spring.redis.cluster.nodes}")
    private Collection<String> redisNodes;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        // RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostName, redisPort);
        // return new JedisConnectionFactory(redisStandaloneConfiguration);
    	RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisNodes);
    	return new JedisConnectionFactory(redisClusterConfiguration);
    }
    
    
//    @Bean
//    @ConfigurationProperties(prefix="redis.cluster")
//    public RedisConnectionFactory redisConnectionFactory(
//            RedisClusterConfiguration redisClusterConfiguration,
//            RedisSentinelConfiguration redisSentinelConfiguration,
//            JedisPoolConfig jedisPoolConfig) {
//
//		// logger.info("Using redis cluster: [{}]", redisClusterConfiguration.getClusterNodes());
//        return new JedisConnectionFactory(redisClusterConfiguration);
//    }

    @Bean(value = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Primary
    @Bean(name = "cacheManager") // Default cache manager is infinite
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().prefixKeysWith(redisPrefix)).build();
    }

    @Bean(name = "cacheManager1Hour")
    public CacheManager cacheManager1Hour(RedisConnectionFactory redisConnectionFactory) {
        Duration expiration = Duration.ofHours(1);
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().prefixKeysWith(redisPrefix).entryTtl(expiration)).build();
    }

}
