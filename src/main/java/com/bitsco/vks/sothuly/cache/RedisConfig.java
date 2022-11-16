package com.bitsco.vks.sothuly.cache;

import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setPassword(StringCommon.isNullOrBlank(password) ? RedisPassword.none() : RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(database);
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(10));// 10s connection timeout

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return jedisConFactory;
    }

    @Bean
    RedisTemplate<String, User> userRedis() {
        final RedisTemplate<String, User> userRedis = new RedisTemplate<String, User>();
        userRedis.setConnectionFactory(jedisConnectionFactory());
        userRedis.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
        return userRedis;
    }

    @Bean
    RedisTemplate<String, Spp> sppRedis() {
        final RedisTemplate<String, Spp> sppRedis = new RedisTemplate<String, Spp>();
        sppRedis.setConnectionFactory(jedisConnectionFactory());
        sppRedis.setValueSerializer(new Jackson2JsonRedisSerializer<Spp>(Spp.class));
        return sppRedis;
    }

    @Bean
    RedisTemplate<String, Law> lawRedis() {
        final RedisTemplate<String, Law> lawRedis = new RedisTemplate<String, Law>();
        lawRedis.setConnectionFactory(jedisConnectionFactory());
        lawRedis.setValueSerializer(new Jackson2JsonRedisSerializer<Law>(Law.class));
        return lawRedis;
    }

    @Bean
    RedisTemplate<String, Case> caseRedis() {
        final RedisTemplate<String, Case> caseRedisTemplate = new RedisTemplate<String, Case>();
        caseRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        caseRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Case>(Case.class));
        return caseRedisTemplate;
    }

    @Bean
    RedisTemplate<String, Accused> accusedRedis() {
        final RedisTemplate<String, Accused> accusedRedisTemplate = new RedisTemplate<String, Accused>();
        accusedRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        accusedRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Accused>(Accused.class));
        return accusedRedisTemplate;
    }

    @Bean
    RedisTemplate<String, Decision> decisionRedis() {
        final RedisTemplate<String, Decision> decisionRedisTemplate = new RedisTemplate<String, Decision>();
        decisionRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        decisionRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Decision>(Decision.class));
        return decisionRedisTemplate;
    }
}
