package com.tests.test_case_helper.controller;

import com.tests.test_case_helper.dto.exception.UserExceptionDTO;
import com.tests.test_case_helper.dto.exception.ValidationExceptionDTO;
import com.tests.test_case_helper.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.ConnectException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class MainControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ValidationExceptionDTO<UserExceptionDTO> onTestSuiteNotFoundException(AccessDeniedException ex) {
        UserExceptionDTO userErrorDTO = new UserExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ValidationExceptionDTO<UserExceptionDTO> onTestSuiteNotFoundException(SQLIntegrityConstraintViolationException ex) {
        UserExceptionDTO userErrorDTO = new UserExceptionDTO(
                ErrorCode.SERVICES_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(ConnectException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ValidationExceptionDTO<UserExceptionDTO> onTestConnectToServicesException(ConnectException ex) {
        UserExceptionDTO userErrorDTO = new UserExceptionDTO(
                ErrorCode.SERVICES_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userErrorDTO));
    }
}
