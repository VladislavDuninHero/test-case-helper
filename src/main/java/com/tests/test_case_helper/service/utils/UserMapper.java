package com.tests.test_case_helper.service.utils;

import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationDTO;
import com.tests.test_case_helper.dto.user.registration.UserRegistrationResponseDTO;
import com.tests.test_case_helper.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    User toEntity(UserRegistrationDTO userRegistrationDTO);

    @Mapping(source = "login", target = "login")
    @Mapping(source = "email", target = "email")
    UserRegistrationResponseDTO toDTO(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "email", target = "email")
    UserDTO toUserDTO(User user);
}
