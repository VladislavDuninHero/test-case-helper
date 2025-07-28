package com.tests.test_case_helper.controller.teams;

import com.tests.test_case_helper.dto.exception.FieldExceptionDTO;
import com.tests.test_case_helper.dto.exception.TeamExceptionDTO;
import com.tests.test_case_helper.dto.exception.UserExceptionDTO;
import com.tests.test_case_helper.dto.exception.ValidationExceptionDTO;
import com.tests.test_case_helper.enums.ErrorCode;
import com.tests.test_case_helper.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(basePackageClasses = TeamController.class)
public class TeamControllerAdvice {

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

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<TeamExceptionDTO> onTeamNotFoundException(TeamNotFoundException ex) {
        TeamExceptionDTO teamExceptionDTO = new TeamExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(teamExceptionDTO));
    }

    @ExceptionHandler(TeammateAlreadyAddedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<TeamExceptionDTO> onTeammateAlreadyAddedException(TeammateAlreadyAddedException ex) {
        TeamExceptionDTO teamExceptionDTO = new TeamExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(teamExceptionDTO));
    }

    @ExceptionHandler(TeamNameIsAlreadyTakenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<TeamExceptionDTO> onTeamNameAlreadyTakenException(TeamNameIsAlreadyTakenException ex) {
        TeamExceptionDTO teamExceptionDTO = new TeamExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(teamExceptionDTO));
    }

    @ExceptionHandler(TeammateNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<TeamExceptionDTO> onTeammateNotFoundException(TeammateNotFoundException ex) {
        TeamExceptionDTO teamExceptionDTO = new TeamExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(teamExceptionDTO));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationExceptionDTO<UserExceptionDTO> onUserNotFoundException(UserNotFoundException ex) {
        UserExceptionDTO userExceptionDTO = new UserExceptionDTO(
                ErrorCode.VALIDATION_ERROR.name(),
                ex.getLocalizedMessage()
        );

        return new ValidationExceptionDTO<>(List.of(userExceptionDTO));
    }
}
