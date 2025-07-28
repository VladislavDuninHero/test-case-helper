package com.tests.test_case_helper.service.user.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.jwt.JwtDTO;
import com.tests.test_case_helper.dto.teams.TeamSlimDTO;
import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.dto.user.UserFullInfoDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginDTO;
import com.tests.test_case_helper.dto.user.login.UserLoginResponseDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;
import com.tests.test_case_helper.entity.User;
import com.tests.test_case_helper.enums.Roles;
import com.tests.test_case_helper.exceptions.UserIsDisabledException;
import com.tests.test_case_helper.repository.UserRepository;
import com.tests.test_case_helper.service.role.RoleService;
import com.tests.test_case_helper.service.security.jwt.JwtService;
import com.tests.test_case_helper.service.user.UserService;
import com.tests.test_case_helper.service.user.UserUtils;
import com.tests.test_case_helper.service.utils.UserMapper;
import com.tests.test_case_helper.service.utils.cache.EvictService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final EvictService evictService;

    public UserServiceImpl(
            UserRepository userRepository,
            UserUtils userUtils,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            RoleService roleService,
            JwtService jwtService,
            EvictService evictService
    ) {
        this.userRepository = userRepository;
        this.userUtils = userUtils;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtService = jwtService;
        this.evictService = evictService;
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
        String password = userLoginDTO.getPassword();

        User foundUser = userUtils.findUserEntityByLoginAndReturn(login);

        if (!foundUser.getIsEnabled()) {
            throw new UserIsDisabledException(ExceptionMessage.USER_IS_DISABLED);
        }

        userUtils.validateUserPassword(password, foundUser.getPassword());

        JwtDTO jwtDTO = new JwtDTO(
                jwtService.generateAccessToken(login)
        );

        evictService.evictProjectsCache(login);

        return new UserLoginResponseDTO(
                login,
                jwtDTO
        );
    }

    @Override
    public UserFullInfoDTO getUserById(Long id) {
        User user = userUtils.findRegisteredUserByIdAndReturn(id);

        Set<TeamSlimDTO> teams = user.getTeams().stream()
                .map(team -> new TeamSlimDTO(
                        team.getTeam().getId(),
                        team.getTeam().getTeamName()
                    )
                )
                .collect(Collectors.toSet());

        return UserFullInfoDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .enabled(user.getIsEnabled())
                .teams(teams)
                .build();
    }

    @Override
    public void disableUser(Long userId) {
        User foundUser = userUtils.findRegisteredUserByIdAndReturn(userId);

        foundUser.setIsEnabled(false);

        userRepository.save(foundUser);
    }

    @Override
    public void recoveryUser(Long userId) {
        User foundUser = userUtils.findRegisteredUserByIdAndReturn(userId);

        foundUser.setIsEnabled(true);

        userRepository.save(foundUser);
    }

    @Override
    public UserDTO getUserByToken(String token) {
        String login = jwtService.getLoginByToken(token);

        return userUtils.findUserByLoginAndReturn(login);
    }

    @Override
    public UserFullInfoDTO getFullUserInfoByToken(String token) {
        String login = jwtService.getLoginByToken(token);

        return userUtils.findFullUserByLoginAndReturn(login);
    }

    @Override
    public UserDTO getUserBySecurityContext() {
        User user = userUtils.findUserBySecurityContextAndReturn();

        return userMapper.toUserDTO(user);
    }
}
