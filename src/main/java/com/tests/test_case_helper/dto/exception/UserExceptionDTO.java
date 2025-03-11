package com.tests.test_case_helper.dto.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExceptionDTO extends AbstractException {
    public UserExceptionDTO(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
