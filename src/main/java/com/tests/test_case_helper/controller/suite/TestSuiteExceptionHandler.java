package com.tests.test_case_helper.controller.suite;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.exception.FieldExceptionDTO;
import com.tests.test_case_helper.dto.exception.TestSuiteExceptionDTO;
import com.tests.test_case_helper.dto.exception.TestSuiteRunSessionExceptionDTO;
import com.tests.test_case_helper.dto.exception.ValidationExceptionDTO;
import com.tests.test_case_helper.enums.ErrorCode;
import com.tests.test_case_helper.exceptions.ActiveTestingSessionIsExistsException;
import com.tests.test_case_helper.exceptions.TestSuiteNotFoundException;
import com.tests.test_case_helper.exceptions.TestSuiteRunSessionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

@RestControllerAdvice(basePackageClasses = TestSuiteController.class)
public class TestSuiteExceptionHandler {

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

    @ExceptionHandler(TestSuiteNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<TestSuiteExceptionDTO> onTestSuiteNotFoundException(TestSuiteNotFoundException ex) {
        TestSuiteExceptionDTO testSuiteErrorDTO = new TestSuiteExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<TestSuiteExceptionDTO> onRunTestSuiteEnvParamNotFoundException(
            MissingServletRequestParameterException ex
    ) {
        TestSuiteExceptionDTO testSuiteErrorDTO = new TestSuiteExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<TestSuiteExceptionDTO> onRunTestSuiteEnvParamIsInvalidException(
            HandlerMethodValidationException ex
    ) {
        TestSuiteExceptionDTO testSuiteErrorDTO = new TestSuiteExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ExceptionMessage.ENVIRONMENT_NOT_FOUND_EXCEPTION_MESSAGE
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }

    @ExceptionHandler(TestSuiteRunSessionNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<TestSuiteExceptionDTO> onRunTestSuiteSessionNotFoundException(
            TestSuiteRunSessionNotFoundException ex
    ) {
        TestSuiteExceptionDTO testSuiteErrorDTO = new TestSuiteExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }

    @ExceptionHandler(ActiveTestingSessionIsExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<TestSuiteRunSessionExceptionDTO> onRunTestSuiteSessionsIsExistsException(
            ActiveTestingSessionIsExistsException ex
    ) {
        TestSuiteRunSessionExceptionDTO testSuiteErrorDTO = new TestSuiteRunSessionExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage(),
                ex.getActiveSessions()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }
}
