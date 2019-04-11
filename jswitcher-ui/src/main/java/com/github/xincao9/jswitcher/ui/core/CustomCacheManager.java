package com.github.xincao9.jswitcher.ui.core;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author xincao9@gmail.com
 */
public class CustomCacheManager {

    private final Map<String, Cache> caches = new ConcurrentHashMap();
    private Cache defaultCache;

    public <K, V> Cache<K, V> getCache(String name) {
        if (caches.containsKey(name)) {
            return caches.get(name);
        }
        if (defaultCache != null) {
            return defaultCache;
        }
        return null;
    }

    public void register(String name, Cache cache) {
        if (!caches.containsKey(name) && cache != null && caches.size() <= 100) {
            caches.put(name, cache);
        }
    }

    public void setDefaultCache(Cache cache) {
        this.defaultCache = cache;
    }

    public Cache getDefaultCache() {
        return this.defaultCache;
    }

}
