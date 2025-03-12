package com.tests.test_case_helper.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ValidationExceptionDTO<T extends AbstractException> {
    private List<T> errors;
}
