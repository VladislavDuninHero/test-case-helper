package com.tests.test_case_helper.service.user.impl;

import com.tests.test_case_helper.dto.jwt.JwtDTO;
import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginResponseDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.enums.Roles;
import com.tests.test_case_helper.repository.UserRepository;
import com.tests.test_case_helper.service.role.RoleService;
import com.tests.test_case_helper.service.security.jwt.JwtService;
import com.tests.test_case_helper.service.user.UserService;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtService jwtService;

    public UserServiceImpl(
            UserRepository userRepository,
            UserUtils userUtils,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            RoleService roleService,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.userUtils = userUtils;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtService = jwtService;
    }

    @Override
    public UserRegistrationResponseDTO createUser(UserRegistrationDTO userRegistrationDTO) {
        userUtils.findRegisteredUser(userRegistrationDTO.getLogin());
        userUtils.validateAdminToken(userRegistrationDTO.getToken());

        User user = userMapper.toEntity(userRegistrationDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        roleService.createRoleForUser(user, Roles.valueOf(userRegistrationDTO.getRole().toUpperCase()));

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserLoginResponseDTO login(
            @RequestBody
            @Validated
            UserLoginDTO userLoginDTO
    ) {
        String login = userLoginDTO.getLogin();

        userUtils.findUserByLogin(login);

        JwtDTO jwtDTO = new JwtDTO(
                jwtService.generateAccessToken(login)
        );

        return new UserLoginResponseDTO(
                login,
                jwtDTO
        );
    }

    @Override
    public UserDTO getUserByToken(String token) {
        String login = jwtService.getLoginByToken(token);

        return userUtils.findUserByLoginAndReturn(login);
    }

}
