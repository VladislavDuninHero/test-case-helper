package com.tests.test_case_helper.dto.cases;

import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCaseDTO {
    private Long id;
    private String title;
    private List<TestCaseDataDTO> testCaseData;
    private List<PreconditionDTO> preconditions;
    private List<StepDTO> steps;
    private List<ExpectedResultDTO> expectedResult;
}
