package com.tests.test_case_helper.dto.cases;

import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestCaseResponseDTO {
    private String title;
    private List<TestCaseDataDTO> testCaseData;
    private List<PreconditionDTO> precondition;
    private List<StepDTO> steps;
    private List<ExpectedResultDTO> expectedResult;
}
