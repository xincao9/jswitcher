package com.github.xincao9.jswitcher.ui.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.xincao9.jswitcher.ui.core.CustomCacheManager;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author xincao9@gmail.com
 */
@Configuration
public class RootConfig {

    /**
     * 
     * @return 
     */
    @Bean
    public CustomCacheManager customCacheManager() {
        CustomCacheManager customCacheManager = new CustomCacheManager();
        Cache defaultCache = Caffeine.newBuilder().recordStats().expireAfterWrite(5, TimeUnit.SECONDS).maximumSize(5000).build();
        customCacheManager.setDefaultCache(defaultCache);
        return customCacheManager;
    }

}