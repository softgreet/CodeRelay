package com.example.demo.Controller;

import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final RedisCacheManager cacheManager;

    public CacheService(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void putInCache(String cacheName, Object key, Object value) {
        cacheManager.getCache(cacheName).put(key, value);
    }

    public Object getFromCache(String cacheName, Object key) {
        return cacheManager.getCache(cacheName).get(key).get();
    }

    public void evictFromCache(String cacheName, Object key) {
        cacheManager.getCache(cacheName).evict(key);
    }

    public void clearCache(String cacheName) {
        cacheManager.getCache(cacheName).clear();
    }
}