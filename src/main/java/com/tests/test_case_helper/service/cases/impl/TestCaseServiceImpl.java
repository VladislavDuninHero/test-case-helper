package com.tests.test_case_helper.service.cases.impl;

import com.tests.test_case_helper.dto.cases.CreateTestCaseDTO;
import com.tests.test_case_helper.dto.cases.CreateTestCaseResponseDTO;
import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.cases.UpdateTestCaseDTO;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.repository.TestCaseRepository;
import com.tests.test_case_helper.service.cases.TestCaseService;
import com.tests.test_case_helper.service.cases.utils.TestCaseUtils;
import com.tests.test_case_helper.service.suite.utils.TestSuiteUtils;
import com.tests.test_case_helper.service.utils.cases.TestCaseMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    private final TestSuiteUtils testSuiteUtils;
    private final TestCaseUtils testCaseUtils;
    private final TestCaseRepository testCaseRepository;
    private final TestCaseMapper testCaseMapper;

    public TestCaseServiceImpl(
            TestSuiteUtils testSuiteUtils,
            TestCaseUtils testCaseUtils,
            TestCaseRepository testCaseRepository,
            TestCaseMapper testCaseMapper
    ) {
        this.testSuiteUtils = testSuiteUtils;
        this.testCaseUtils = testCaseUtils;
        this.testCaseRepository = testCaseRepository;
        this.testCaseMapper = testCaseMapper;
    }

    @Override
    public CreateTestCaseResponseDTO create(CreateTestCaseDTO createTestCaseDTO) {
        TestSuite testSuite = testSuiteUtils.getTestSuiteById(createTestCaseDTO.getTestSuiteId());

        TestCase testCase = TestCase.builder()
                .title(createTestCaseDTO.getTitle())
                .testSuite(testSuite)
                .testCaseData(testCaseUtils.testCaseDataMapper(createTestCaseDTO.getTestCaseData()))
                .testCasePrecondition(testCaseUtils.testCasePreconditionMapper(createTestCaseDTO.getPrecondition()))
                .steps(testCaseUtils.testCaseStepMapper(createTestCaseDTO.getSteps()))
                .expectedResult(testCaseUtils.testCaseExpectedResultMapper(createTestCaseDTO.getExpectedResult()))
                .build();

        TestCase createdTestCase = testCaseRepository.save(testCase);

        CreateTestCaseResponseDTO createTestCaseResponseDTO = testCaseMapper.toDto(createdTestCase);
        createTestCaseResponseDTO.setProjectId(testSuite.getProject().getId());
        createTestCaseResponseDTO.setTestSuiteId(testSuite.getId());

        return createTestCaseResponseDTO;
    }

    @Override
    public TestCaseDTO getTestCase(Long id) {
        TestCase foundTestCase = testCaseUtils.getTestCaseById(id);

        return TestCaseDTO.builder()
                .id(foundTestCase.getId())
                .title(foundTestCase.getTitle())
                .testCaseData(testCaseUtils.testCaseDataMapperToDTO(foundTestCase.getTestCaseData()))
                .preconditions(testCaseUtils.testCasePreconditionMapperToDTO(foundTestCase.getTestCasePrecondition()))
                .steps(testCaseUtils.testCaseStepMapperToDTO(foundTestCase.getSteps()))
                .expectedResult(testCaseUtils.testCaseExpectedResultMapperToDTO(foundTestCase.getExpectedResult()))
                .build();
    }

    @Override
    @Transactional
    public TestCaseDTO updateTestCase(Long id, UpdateTestCaseDTO updateTestCaseDTO) {
        TestCase foundTestCase = testCaseUtils.getTestCaseById(id);

        foundTestCase.setTitle(updateTestCaseDTO.getTitle());

        foundTestCase.getTestCaseData().clear();
        foundTestCase.getTestCaseData()
                .addAll(testCaseUtils.testCaseDataMapper(updateTestCaseDTO.getTestCaseData()));

        foundTestCase.getTestCasePrecondition().clear();
        foundTestCase.getTestCasePrecondition()
                .addAll(testCaseUtils.testCasePreconditionMapper(updateTestCaseDTO.getPreconditions()));

        foundTestCase.getSteps().clear();
        foundTestCase.getSteps().addAll(testCaseUtils.testCaseStepMapper(updateTestCaseDTO.getSteps()));

        foundTestCase.getExpectedResult().clear();
        foundTestCase.getExpectedResult()
                .addAll(testCaseUtils.testCaseExpectedResultMapper(updateTestCaseDTO.getExpectedResult()));

        TestCase updatedTestCase = testCaseRepository.save(foundTestCase);

        return TestCaseDTO.builder()
                .id(updatedTestCase.getId())
                .title(updatedTestCase.getTitle())
                .testCaseData(testCaseUtils.testCaseDataMapperToDTO(updatedTestCase.getTestCaseData()))
                .preconditions(testCaseUtils.testCasePreconditionMapperToDTO(updatedTestCase.getTestCasePrecondition()))
                .steps(testCaseUtils.testCaseStepMapperToDTO(updatedTestCase.getSteps()))
                .expectedResult(testCaseUtils.testCaseExpectedResultMapperToDTO(updatedTestCase.getExpectedResult()))
                .build();
    }

    @Override
    @Transactional
    public void deleteTestCase(Long id) {
        TestCase foundTestCase = testCaseUtils.getTestCaseById(id);

        foundTestCase.getTestCaseData().clear();
        foundTestCase.getTestCasePrecondition().clear();
        foundTestCase.getSteps().clear();
        foundTestCase.getExpectedResult().clear();

        testCaseRepository.delete(foundTestCase);
    }

    @Override
    public List<TestCaseDTO> getTestCasesByTestSuiteId(Long testSuiteId, Pageable pageable) {
        List<TestCase> testCases = testCaseRepository.getAllTestCasesByTestSuiteId(testSuiteId, pageable);

        List<TestCaseDTO> mappedTestCases = new ArrayList<>();

        for (TestCase testCase : testCases) {
            TestCaseDTO testCaseDTO = TestCaseDTO.builder()
                    .id(testCase.getId())
                    .title(testCase.getTitle())
                    .testCaseData(testCaseUtils.testCaseDataMapperToDTO(testCase.getTestCaseData()))
                    .preconditions(testCaseUtils.testCasePreconditionMapperToDTO(testCase.getTestCasePrecondition()))
                    .steps(testCaseUtils.testCaseStepMapperToDTO(testCase.getSteps()))
                    .expectedResult(testCaseUtils.testCaseExpectedResultMapperToDTO(testCase.getExpectedResult()))
                    .build();

            mappedTestCases.add(testCaseDTO);
        }

        return mappedTestCases;
    }

    @Override
    public List<TestCaseDTO> getTestCasesByTestSuiteId(Long testSuiteId) {
        List<TestCase> testCases = testCaseRepository.getAllTestCasesByTestSuiteId(testSuiteId);

        List<TestCaseDTO> mappedTestCases = new ArrayList<>();

        for (TestCase testCase : testCases) {
            TestCaseDTO testCaseDTO = TestCaseDTO.builder()
                    .id(testCase.getId())
                    .title(testCase.getTitle())
                    .testCaseData(testCaseUtils.testCaseDataMapperToDTO(testCase.getTestCaseData()))
                    .preconditions(testCaseUtils.testCasePreconditionMapperToDTO(testCase.getTestCasePrecondition()))
                    .steps(testCaseUtils.testCaseStepMapperToDTO(testCase.getSteps()))
                    .expectedResult(testCaseUtils.testCaseExpectedResultMapperToDTO(testCase.getExpectedResult()))
                    .build();

            mappedTestCases.add(testCaseDTO);
        }

        return mappedTestCases;
    }


}
