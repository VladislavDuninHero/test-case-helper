package com.tests.test_case_helper.service.suite.impl;

import com.tests.test_case_helper.dto.suite.CreateTestSuiteDTO;
import com.tests.test_case_helper.dto.suite.CreateTestSuiteResponseDTO;
import com.tests.test_case_helper.dto.suite.ExtendedTestSuiteDTO;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.enums.Tag;
import com.tests.test_case_helper.repository.TestSuiteRepository;
import com.tests.test_case_helper.service.cases.TestCaseService;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import com.tests.test_case_helper.service.suite.utils.impl.TestSuiteUtil;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import org.springframework.stereotype.Service;


@Service
public class TestSuiteServiceImpl implements TestSuiteService {

    private final TestSuiteRepository testSuiteRepository;
    private final ProjectUtils projectUtils;
    private final TestSuiteMapper testSuiteMapper;
    private final TestSuiteUtil testSuiteUtil;
    private final TestCaseService testCaseService;

    public TestSuiteServiceImpl(
            TestSuiteRepository testSuiteRepository,
            ProjectUtils projectUtils,
            TestSuiteMapper testSuiteMapper,
            TestSuiteUtil testSuiteUtil,
            TestCaseService testCaseService
    ) {
        this.testSuiteRepository = testSuiteRepository;
        this.projectUtils = projectUtils;
        this.testSuiteMapper = testSuiteMapper;
        this.testSuiteUtil = testSuiteUtil;
        this.testCaseService = testCaseService;
    }

    @Override
    public CreateTestSuiteResponseDTO createTestSuite(CreateTestSuiteDTO createTestSuiteDTO) {
        Project project = projectUtils.getProjectById(createTestSuiteDTO.getProjectId());

        TestSuite testSuite = TestSuite.builder()
                .title(createTestSuiteDTO.getTitle())
                .description(createTestSuiteDTO.getDescription())
                .project(project)
                .tag(Tag.valueOf(createTestSuiteDTO.getTag()))
                .build();

        TestSuite createdTestSuit = testSuiteRepository.save(testSuite);

        CreateTestSuiteResponseDTO createTestSuiteResponseDTO = testSuiteMapper.toDto(createdTestSuit);
        createTestSuiteResponseDTO.setProjectId(project.getId());
        
        return createTestSuiteResponseDTO;
    }

    @Override
    public ExtendedTestSuiteDTO getTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);

        return ExtendedTestSuiteDTO.builder()
                .title(testSuite.getTitle())
                .description(testSuite.getDescription())
                .projectId(testSuite.getProject().getId())
                .testCases(testCaseService.getTestCasesByTestSuiteId(id))
                .build();
    }

}
