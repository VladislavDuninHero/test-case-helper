package com.tests.test_case_helper.service.cases.utils;

import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import com.tests.test_case_helper.entity.cases.TestCaseData;
import com.tests.test_case_helper.entity.cases.TestCaseExpectedResult;
import com.tests.test_case_helper.entity.cases.TestCasePrecondition;
import com.tests.test_case_helper.entity.cases.TestCaseStep;

import java.util.List;

public interface TestCaseUtils {
    List<TestCaseData> testCaseDataMapper(List<TestCaseDataDTO> testCaseDataDTO);
    List<TestCaseDataDTO> testCaseDataMapperToDTO(List<TestCaseData> testCaseData);
    List<TestCasePrecondition> testCasePreconditionMapper(List<PreconditionDTO> preconditionDTO);
    List<PreconditionDTO> testCasePreconditionMapperToDTO(List<TestCasePrecondition> preconditions);
    List<TestCaseStep> testCaseStepMapper(List<StepDTO> stepDTO);
    List<StepDTO> testCaseStepMapperToDTO(List<TestCaseStep> steps);
    List<TestCaseExpectedResult> testCaseExpectedResultMapper(List<ExpectedResultDTO> expectedResultDTO);
    List<ExpectedResultDTO> testCaseExpectedResultMapperToDTO(List<TestCaseExpectedResult> expectedResults);

}
