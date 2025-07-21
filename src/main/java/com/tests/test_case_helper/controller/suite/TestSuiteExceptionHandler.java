package com.tests.test_case_helper.controller.suite;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.exception.*;
import com.tests.test_case_helper.enums.ErrorCode;
import com.tests.test_case_helper.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
                ExceptionMessage.TEST_CASE_RESULT_MISSING_REQUIRED_FIELD_EXCEPTION_MESSAGE
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

    @ExceptionHandler(TestCaseResultNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<TestSuiteExceptionDTO> onRunTestSuiteSessionRunResultNotFoundException(
            TestCaseResultNotFoundException ex
    ) {
        TestSuiteExceptionDTO testSuiteErrorDTO = new TestSuiteExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<TestSuiteExceptionDTO> onRunTestSuiteSessionNotFoundException(
            HttpMessageNotReadableException ex
    ) {
        TestSuiteExceptionDTO testSuiteErrorDTO = new TestSuiteExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }

    @ExceptionHandler(TestSuiteRunSessionAlreadyEndedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDTO<TestSuiteExceptionDTO> onRunTestSuiteSessionIsEndedException(
            TestSuiteRunSessionAlreadyEndedException ex
    ) {
        TestSuiteExceptionDTO testSuiteErrorDTO = new TestSuiteExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<ProjectExceptionDTO> onProjectNotFoundException(
            ProjectNotFoundException ex
    ) {
        ProjectExceptionDTO testSuiteErrorDTO = new ProjectExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(testSuiteErrorDTO));
    }
}
