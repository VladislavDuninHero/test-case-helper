package com.tests.test_case_helper.service.role.factory;

import com.tests.test_case_helper.enums.PermissionType;
import com.tests.test_case_helper.enums.Roles;

import java.util.List;

public interface RolePermissionsFactory {
    List<PermissionType> getRolePermissions(Roles role);
}
