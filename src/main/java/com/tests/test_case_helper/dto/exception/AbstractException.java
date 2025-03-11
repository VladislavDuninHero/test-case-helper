package com.tests.test_case_helper.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractException {
    public String errorCode;
    public String errorMessage;
}
