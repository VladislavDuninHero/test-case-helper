package com.tests.test_case_helper.service.suite.impl;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.suite.*;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.enums.Tag;
import com.tests.test_case_helper.repository.TestSuiteRepository;
import com.tests.test_case_helper.service.cases.TestCaseService;
import com.tests.test_case_helper.service.project.utils.ProjectUtils;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import com.tests.test_case_helper.service.suite.utils.impl.TestSuiteUtil;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


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
    @Transactional
    public TestSuiteDTO updateTestSuiteById(Long id, UpdateTestSuiteDTO updateTestSuiteDTO) {
        TestSuite foundTestSuite = testSuiteUtil.getTestSuiteById(id);

        foundTestSuite.setTitle(updateTestSuiteDTO.getTitle());
        foundTestSuite.setDescription(updateTestSuiteDTO.getDescription());
        foundTestSuite.setTag(Tag.valueOf(updateTestSuiteDTO.getTag().toUpperCase()));

        TestSuite updatedTestSuite = testSuiteRepository.save(foundTestSuite);

        return testSuiteMapper.toBaseTestSuiteDTO(updatedTestSuite);
    }

    @Override
    public ExtendedTestSuiteDTO getTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);
        List<TestCaseDTO> testCases = testCaseService.getTestCasesByTestSuiteId(id);

        return ExtendedTestSuiteDTO.builder()
                .title(testSuite.getTitle())
                .description(testSuite.getDescription())
                .projectId(testSuite.getProject().getId())
                .numberOfTestCases(testSuite.getTestsCases().size())
                .testCases(testCases)
                .build();
    }

    @Override
    public ExtendedTestSuiteDTO getTestSuite(Long id, Pageable pageable) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);
        List<TestCaseDTO> testCases = testCaseService.getTestCasesByTestSuiteId(id, pageable);

        return ExtendedTestSuiteDTO.builder()
                .title(testSuite.getTitle())
                .description(testSuite.getDescription())
                .projectId(testSuite.getProject().getId())
                .numberOfTestCases(testSuite.getTestsCases().size())
                .testCases(testCases)
                .build();
    }

    @Override
    @Transactional
    public void deleteTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);

        testSuiteRepository.delete(testSuite);
    }

    @Override
    public TestSuiteDTO getSlimTestSuite(Long id) {
        TestSuite testSuite = testSuiteUtil.getTestSuiteById(id);

        return TestSuiteDTO.builder()
                .id(testSuite.getId())
                .title(testSuite.getTitle())
                .description(testSuite.getDescription())
                .tag(testSuite.getTag().name())
                .build();
    }

}
