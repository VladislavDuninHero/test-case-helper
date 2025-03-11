package com.tests.test_case_helper.service.role.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.entity.Permission;
import com.tests.test_case_helper.entity.Role;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.enums.Roles;
import com.tests.test_case_helper.exceptions.UserNotFoundException;
import com.tests.test_case_helper.repository.UserRepository;
import com.tests.test_case_helper.service.role.RoleService;
import com.tests.test_case_helper.service.role.factory.RolePermissionsFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RolePermissionsFactory rolePermissionsFactory;
    private final UserRepository userRepository;

    public RoleServiceImpl(RolePermissionsFactory rolePermissionsFactory, UserRepository userRepository) {
        this.rolePermissionsFactory = rolePermissionsFactory;
        this.userRepository = userRepository;
    }

    @Override
    public void createRoleForUser(User user, Roles role) {
        Role roleEntity = new Role();
        roleEntity.setRole(role);

        Set<Permission> permissions = rolePermissionsFactory.getRolePermissions(role)
                .stream()
                .map(Permission::new)
                .collect(Collectors.toSet());

        roleEntity.setPermissions(permissions);

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        user.getRoles().add(roleEntity);
    }

    @Override
    public Set<Role> getRoleForUser(String login) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND_EXCEPTION_MESSAGE));

        return user.getRoles();
    }
}
