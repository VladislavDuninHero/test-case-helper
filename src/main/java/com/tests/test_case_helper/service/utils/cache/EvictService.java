package com.tests.test_case_helper.service.utils.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class EvictService {

    private final CacheManager cacheManager;

    public EvictService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void evictProjectCache(Long projectId) {
        Cache cache = cacheManager.getCache("project");

        if (cache != null) {
            cache.evict(projectId);
        }
    }
}
