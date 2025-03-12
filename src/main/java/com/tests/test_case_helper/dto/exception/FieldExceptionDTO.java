package com.tests.test_case_helper.dto.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldExceptionDTO extends AbstractException {

    private String field;

    public FieldExceptionDTO(
            String field,
            String message,
            String errorCode
    ) {
        super(errorCode, message);
        this.field = field;
    }
}
