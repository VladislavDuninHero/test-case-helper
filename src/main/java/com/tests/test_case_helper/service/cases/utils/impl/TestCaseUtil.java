package com.tests.test_case_helper.service.cases.utils.impl;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import com.tests.test_case_helper.entity.cases.*;
import com.tests.test_case_helper.exceptions.TestCaseNotFoundException;
import com.tests.test_case_helper.repository.TestCaseRepository;
import com.tests.test_case_helper.service.cases.utils.TestCaseUtils;
import com.tests.test_case_helper.service.utils.cases.ExpectedResultMapper;
import com.tests.test_case_helper.service.utils.cases.PreconditionMapper;
import com.tests.test_case_helper.service.utils.cases.StepMapper;
import com.tests.test_case_helper.service.utils.cases.TestCaseDataMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestCaseUtil implements TestCaseUtils {

    private final TestCaseDataMapper testCaseDataMapper;
    private final PreconditionMapper preconditionMapper;
    private final StepMapper stepMapper;
    private final ExpectedResultMapper expectedResultMapper;
    private final TestCaseRepository testCaseRepository;

    public TestCaseUtil(
            TestCaseDataMapper testCaseDataMapper,
            PreconditionMapper preconditionMapper,
            StepMapper stepMapper,
            ExpectedResultMapper expectedResultMapper,
            TestCaseRepository testCaseRepository) {
        this.testCaseDataMapper = testCaseDataMapper;
        this.preconditionMapper = preconditionMapper;
        this.stepMapper = stepMapper;
        this.expectedResultMapper = expectedResultMapper;
        this.testCaseRepository = testCaseRepository;
    }

    @Override
    public List<TestCaseData> testCaseDataMapper(List<TestCaseDataDTO> testCaseDataDTO) {
        return testCaseDataDTO
                .stream()
                .map(testCaseDataMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestCaseDataDTO> testCaseDataMapperToDTO(List<TestCaseData> testCaseData) {
        return testCaseData
                .stream()
                .map(testCaseDataMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestCasePrecondition> testCasePreconditionMapper(List<PreconditionDTO> preconditionDTO) {
        return preconditionDTO
                .stream()
                .map(preconditionMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PreconditionDTO> testCasePreconditionMapperToDTO(List<TestCasePrecondition> preconditions) {
        return preconditions
                .stream()
                .map(preconditionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestCaseStep> testCaseStepMapper(List<StepDTO> stepDTO) {
        return stepDTO
                .stream()
                .map(stepMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<StepDTO> testCaseStepMapperToDTO(List<TestCaseStep> steps) {
        return steps
                .stream()
                .map(stepMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestCaseExpectedResult> testCaseExpectedResultMapper(List<ExpectedResultDTO> expectedResultDTO) {
        return expectedResultDTO
                .stream()
                .map(expectedResultMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpectedResultDTO> testCaseExpectedResultMapperToDTO(List<TestCaseExpectedResult> expectedResults) {
        return expectedResults
                .stream()
                .map(expectedResultMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TestCase getTestCaseById(Long id) {
        return testCaseRepository.getTestCaseById(id)
                .orElseThrow(() -> new TestCaseNotFoundException(ExceptionMessage.TEST_CASE_NOT_FOUND_EXCEPTION_MESSAGE));
    }
}
