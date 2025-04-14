package com.tests.test_case_helper.controller.user;

import com.tests.test_case_helper.dto.exception.FieldExceptionDTO;
import com.tests.test_case_helper.dto.exception.UserExceptionDTO;
import com.tests.test_case_helper.dto.exception.ValidationExceptionDTO;
import com.tests.test_case_helper.enums.ErrorCode;
import com.tests.test_case_helper.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice(basePackageClasses = {UserController.class})
public class UserControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<FieldExceptionDTO> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<FieldExceptionDTO> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(
                        error -> new FieldExceptionDTO(
                                error.getField(),
                                error.getDefaultMessage(),
                                ErrorCode.VALIDATION_ERROR.name()
                        )
                )
                .toList();

        return new ValidationExceptionDTO<>(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<UserExceptionDTO> onUserNotFoundException(UserNotFoundException ex) {
        UserExceptionDTO userErrorDTO = new UserExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(UserIsDisabledException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<UserExceptionDTO> onUserIsDisabledException(UserIsDisabledException ex) {
        UserExceptionDTO userErrorDTO = new UserExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(UserIsAlreadyRegisteredException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<UserExceptionDTO> onUserIsAlreadyRegisteredException(UserIsAlreadyRegisteredException ex) {
        UserExceptionDTO userErrorDTO = new UserExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userErrorDTO));
    }

    @ExceptionHandler(UserLoginDataIsInvalidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<UserExceptionDTO> onUserLoginDataIsInvalidException(UserLoginDataIsInvalidException ex) {
        UserExceptionDTO userErrorDTO = new UserExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userErrorDTO));
    }
}
