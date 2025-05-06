package com.tests.test_case_helper.controller.converter;

import com.tests.test_case_helper.dto.exception.ProjectExceptionDTO;
import com.tests.test_case_helper.dto.exception.ValidationExceptionDTO;
import com.tests.test_case_helper.enums.ErrorCode;
import com.tests.test_case_helper.exceptions.ExcelFileParsedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.List;

@RestControllerAdvice(basePackageClasses = ConvertersController.class)
public class ConverterControllerExceptionHandler {

    @ExceptionHandler(ExcelFileParsedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<ProjectExceptionDTO> onExcelFileParsedException(ExcelFileParsedException ex) {
        ProjectExceptionDTO error = new ProjectExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(error));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<ProjectExceptionDTO> onExcelFileParsedException(MissingServletRequestPartException ex) {
        ProjectExceptionDTO error = new ProjectExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(error));
    }
}
