package com.tests.test_case_helper.service.role;

import com.tests.test_case_helper.entity.Role;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.enums.Roles;

import java.util.Set;

public interface RoleService {
    void createRoleForUser(User user, Roles role);
    Set<Role> getRoleForUser(String login);
}
