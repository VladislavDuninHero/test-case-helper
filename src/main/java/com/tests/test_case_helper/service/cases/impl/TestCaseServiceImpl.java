package com.tests.test_case_helper.service.cases.impl;

import com.tests.test_case_helper.dto.cases.CreateTestCaseDTO;
import com.tests.test_case_helper.dto.cases.CreateTestCaseResponseDTO;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.repository.TestCaseRepository;
import com.tests.test_case_helper.service.cases.TestCaseService;
import com.tests.test_case_helper.service.cases.utils.TestCaseUtils;
import com.tests.test_case_helper.service.suite.utils.TestSuiteUtils;
import com.tests.test_case_helper.service.utils.cases.TestCaseMapper;
import org.springframework.stereotype.Service;

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

}
