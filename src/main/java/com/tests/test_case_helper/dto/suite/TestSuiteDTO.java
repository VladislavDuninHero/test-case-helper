package com.tests.test_case_helper.dto.suite;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestSuiteDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
}
