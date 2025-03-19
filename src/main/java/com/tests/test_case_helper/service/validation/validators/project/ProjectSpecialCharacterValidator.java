package com.tests.test_case_helper.service.validation.validators.project;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.exceptions.InvalidSpecialCharactersException;
import com.tests.test_case_helper.service.validation.validators.BaseValidator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
@Component
public class ProjectSpecialCharacterValidator extends BaseValidator<CreateProjectDTO, CreateProjectDTO>{

    private final Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я0-9_\\s]+$");

    @Override
    public CreateProjectDTO validate(CreateProjectDTO value) {

        Matcher titleMatcher = pattern.matcher(value.getTitle());
        Matcher descriptionMatcher = pattern.matcher(value.getDescription());

        if (!titleMatcher.matches() || !descriptionMatcher.matches()) {
            throw new InvalidSpecialCharactersException(ExceptionMessage.INVALID_SPECIAL_CHARACTERS_EXCEPTION_MESSAGE);
        }

        if (getNext() != null) {
            return getNext().validate(value);
        }

        return value;
    }
}
