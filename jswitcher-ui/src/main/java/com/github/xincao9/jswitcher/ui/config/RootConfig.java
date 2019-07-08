/*
 * Copyright 2019 xincao9@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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