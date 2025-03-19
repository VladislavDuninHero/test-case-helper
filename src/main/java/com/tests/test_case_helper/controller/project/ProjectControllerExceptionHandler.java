package com.tests.test_case_helper.controller.project;

import com.tests.test_case_helper.dto.exception.FieldExceptionDTO;
import com.tests.test_case_helper.dto.exception.ProjectExceptionDTO;
import com.tests.test_case_helper.dto.exception.ValidationExceptionDTO;
import com.tests.test_case_helper.enums.ErrorCode;
import com.tests.test_case_helper.exceptions.InvalidSpecialCharactersException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(basePackageClasses = ProjectController.class)
public class ProjectControllerExceptionHandler {

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

    @ExceptionHandler(InvalidSpecialCharactersException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<ProjectExceptionDTO> onMethodArgumentNotValidException(InvalidSpecialCharactersException ex) {
        ProjectExceptionDTO userErrorDTO = new ProjectExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userErrorDTO));
    }
}
