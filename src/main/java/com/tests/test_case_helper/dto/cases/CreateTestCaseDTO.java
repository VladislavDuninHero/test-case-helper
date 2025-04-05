package com.tests.test_case_helper.dto.cases;

import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestCaseDTO {

    @NotNull
    @Positive
    private Long testSuiteId;

    @NotEmpty
    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    private List<TestCaseDataDTO> testCaseData;

    @NotNull
    private List<PreconditionDTO> precondition;

    @NotNull
    private List<StepDTO> steps;

    @NotNull
    private List<ExpectedResultDTO> expectedResult;
}
