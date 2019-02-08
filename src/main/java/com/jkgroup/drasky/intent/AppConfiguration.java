package com.jkgroup.drasky.intent;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.jkgroup.drasky.commuting.bus.mpkcracow.MpkCracowConnector;
import com.jkgroup.drasky.health.service.airly.AirlyService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfiguration {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    public static final String NOT_EXPIRING_CACHE_BEAN_NAME = "NOT_EXPIRING_CACHE_BEAN_NAME";
    public static final String EXPIRING_CACHE_BEAN_NAME = "EXPIRING_CACHE_BEAN_NAME";

    @Bean
    public TemplateEngine templateEngine(){
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver ();
        templateResolver.setTemplateMode("TEXT");
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new Java8TimeDialect());

        return templateEngine;
    }

    @Bean(NOT_EXPIRING_CACHE_BEAN_NAME)
    public CacheManager notExpiringCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(MpkCracowConnector.CACHE_NAME);
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1_000)
                .expireAfterWrite(Duration.ofDays(2))
                .expireAfterAccess(Duration.ofDays(2)));

        return cacheManager;
    }

    @Bean(EXPIRING_CACHE_BEAN_NAME)
    @Primary
    public CacheManager expiringCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(AirlyService.CACHE_NAME);
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(Duration.ofSeconds(1800)));

        return cacheManager;
    }
}
