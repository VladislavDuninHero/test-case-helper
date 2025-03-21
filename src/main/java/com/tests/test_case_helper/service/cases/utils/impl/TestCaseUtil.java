package com.tests.test_case_helper.service.cases.utils.impl;

import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import com.tests.test_case_helper.entity.cases.TestCaseData;
import com.tests.test_case_helper.entity.cases.TestCaseExpectedResult;
import com.tests.test_case_helper.entity.cases.TestCasePrecondition;
import com.tests.test_case_helper.entity.cases.TestCaseStep;
import com.tests.test_case_helper.service.cases.utils.TestCaseUtils;
import com.tests.test_case_helper.service.utils.cases.ExpectedResultMapper;
import com.tests.test_case_helper.service.utils.cases.PreconditionMapper;
import com.tests.test_case_helper.service.utils.cases.StepMapper;
import com.tests.test_case_helper.service.utils.cases.TestCaseDataMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestCaseUtil implements TestCaseUtils {

    private final TestCaseDataMapper testCaseDataMapper;
    private final PreconditionMapper preconditionMapper;
    private final StepMapper stepMapper;
    private final ExpectedResultMapper expectedResultMapper;

    public TestCaseUtil(
            TestCaseDataMapper testCaseDataMapper,
            PreconditionMapper preconditionMapper,
            StepMapper stepMapper,
            ExpectedResultMapper expectedResultMapper
    ) {
        this.testCaseDataMapper = testCaseDataMapper;
        this.preconditionMapper = preconditionMapper;
        this.stepMapper = stepMapper;
        this.expectedResultMapper = expectedResultMapper;
    }

    @Override
    public List<TestCaseData> testCaseDataMapper(List<TestCaseDataDTO> testCaseDataDTO) {
        return testCaseDataDTO
                .stream()
                .map(testCaseDataMapper::toEntity)
                .toList();
    }

    @Override
    public List<TestCaseDataDTO> testCaseDataMapperToDTO(List<TestCaseData> testCaseData) {
        return testCaseData
                .stream()
                .map(testCaseDataMapper::toDto)
                .toList();
    }

    @Override
    public List<TestCasePrecondition> testCasePreconditionMapper(List<PreconditionDTO> preconditionDTO) {
        return preconditionDTO
                .stream()
                .map(preconditionMapper::toEntity)
                .toList();
    }

    @Override
    public List<PreconditionDTO> testCasePreconditionMapperToDTO(List<TestCasePrecondition> preconditions) {
        return preconditions
                .stream()
                .map(preconditionMapper::toDto)
                .toList();
    }

    @Override
    public List<TestCaseStep> testCaseStepMapper(List<StepDTO> stepDTO) {
        return stepDTO
                .stream()
                .map(stepMapper::toEntity)
                .toList();
    }

    @Override
    public List<StepDTO> testCaseStepMapperToDTO(List<TestCaseStep> steps) {
        return steps
                .stream()
                .map(stepMapper::toDto)
                .toList();
    }

    @Override
    public List<TestCaseExpectedResult> testCaseExpectedResultMapper(List<ExpectedResultDTO> expectedResultDTO) {
        return expectedResultDTO
                .stream()
                .map(expectedResultMapper::toEntity)
                .toList();
    }

    @Override
    public List<ExpectedResultDTO> testCaseExpectedResultMapperToDTO(List<TestCaseExpectedResult> expectedResults) {
        return expectedResults
                .stream()
                .map(expectedResultMapper::toDto)
                .toList();
    }
}
