package com.tests.test_case_helper.service.utils.cache;

import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.entity.UserTeam;
import com.tests.test_case_helper.service.user.UserUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EvictService {

    private final CacheManager cacheManager;
    private final UserUtils userUtils;

    public EvictService(
            CacheManager cacheManager,
            UserUtils userUtils
    ) {
        this.cacheManager = cacheManager;
        this.userUtils = userUtils;
    }

    public void evictProjectCache() {
        Cache cache = cacheManager.getCache("project");
        User user = userUtils.findUserBySecurityContextAndReturn();

        if (cache != null) {
            cache.evict("project::" + user.getLogin());
        }
    }

    public void evictProjectsCache(String login) {
        Cache cache = cacheManager.getCache("user_projects");

        if (cache != null) {
            cache.evict("user_projects::" + login);
        }
    }

    public void evictTeamCache(Set<UserTeam> team) {
        for (UserTeam userTeam : team) {
            String login = userTeam.getUser().getLogin();

            Cache cache = cacheManager.getCache("user_projects");
            if (cache != null) {
                cache.evict(login);
            }

            Cache projectCache = cacheManager.getCache("project");
            if (projectCache != null) {
                userTeam.getTeam()
                        .getProjects()
                        .forEach(project -> projectCache.evict(project.getId() + "::" + login));
            }
        }
    }
}
