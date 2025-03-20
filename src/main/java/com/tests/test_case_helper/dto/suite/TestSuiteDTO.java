package com.tests.test_case_helper.dto.suite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestSuiteDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
}
