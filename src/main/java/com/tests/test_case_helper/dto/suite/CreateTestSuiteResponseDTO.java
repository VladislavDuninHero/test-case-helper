package com.tests.test_case_helper.dto.suite;

import com.tests.test_case_helper.enums.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestSuiteResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Tag tag;
    private Long projectId;
}
