package com.tests.test_case_helper.controller.cases;

import com.tests.test_case_helper.dto.exception.FieldExceptionDTO;
import com.tests.test_case_helper.dto.exception.TestCaseExceptionDTO;
import com.tests.test_case_helper.dto.exception.TestSuiteExceptionDTO;
import com.tests.test_case_helper.dto.exception.ValidationExceptionDTO;
import com.tests.test_case_helper.enums.ErrorCode;
import com.tests.test_case_helper.exceptions.TestCaseNotFoundException;
import com.tests.test_case_helper.exceptions.TestSuiteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice(basePackageClasses = {TestCaseController.class})
public class TestCaseControllerExceptionHandler {

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

    @ExceptionHandler(TestCaseNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<TestCaseExceptionDTO> onTestCaseNotFoundException(TestCaseNotFoundException ex) {
        TestCaseExceptionDTO error = new TestCaseExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(error));
    }

    @ExceptionHandler(TestSuiteNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<TestSuiteExceptionDTO> onTestSuiteNotFoundException(TestSuiteNotFoundException ex) {
        TestSuiteExceptionDTO testSuiteErrorDTO = new TestSuiteExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }
}
