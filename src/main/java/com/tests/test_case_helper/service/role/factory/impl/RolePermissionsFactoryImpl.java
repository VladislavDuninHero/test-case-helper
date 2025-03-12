package com.tests.test_case_helper.service.role.factory.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.enums.PermissionType;
import com.tests.test_case_helper.enums.Roles;
import com.tests.test_case_helper.exceptions.NotImplementedException;
import com.tests.test_case_helper.service.role.factory.RolePermissionsFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RolePermissionsFactoryImpl implements RolePermissionsFactory {

    private final Map<Roles, List<PermissionType>> rolePermissions = Map.ofEntries(
            Map.entry(
                    Roles.QA, List.of(
                            PermissionType.CREATE_TEST_SUITE,
                            PermissionType.READ_TEST_SUITE,
                            PermissionType.UPDATE_TEST_SUITE,
                            PermissionType.DELETE_TEST_SUITE,
                            PermissionType.CREATE_TEST_CASES,
                            PermissionType.READ_TEST_CASES,
                            PermissionType.UPDATE_TEST_CASES,
                            PermissionType.DELETE_TEST_CASES,
                            PermissionType.CREATE_PROJECT,
                            PermissionType.READ_PROJECT,
                            PermissionType.UPDATE_PROJECT,
                            PermissionType.DELETE_PROJECT
                    )
            )
    );

    @Override
    public List<PermissionType> getRolePermissions(Roles role) {
        if (!rolePermissions.containsKey(role)) {
            throw new NotImplementedException(
                    String.format(ExceptionMessage.ROLE_IS_NOT_IMPLEMENTED_EXCEPTION_MESSAGE, role)
            );
        }

        return rolePermissions.get(role);
    }
}
