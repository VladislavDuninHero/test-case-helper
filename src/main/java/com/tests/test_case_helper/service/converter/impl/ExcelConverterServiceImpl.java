package com.tests.test_case_helper.service.converter.impl;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.project.ExtendedProjectDTO;
import com.tests.test_case_helper.dto.suite.TestSuiteDTO;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.repository.TestSuiteRepository;
import com.tests.test_case_helper.service.cases.TestCaseService;
import com.tests.test_case_helper.service.converter.ExcelConverterService;
import com.tests.test_case_helper.service.converter.util.ExcelConverterServiceUtil;
import com.tests.test_case_helper.service.project.utils.impl.ProjectUtil;
import com.tests.test_case_helper.service.utils.ProjectMapper;
import com.tests.test_case_helper.service.utils.TestSuiteMapper;
import com.tests.test_case_helper.service.utils.cache.EvictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelConverterServiceImpl implements ExcelConverterService {

    private final ProjectUtil projectUtil;
    private final TestCaseService testCaseService;
    private final ExcelConverterServiceUtil excelConverterServiceUtil;
    private final ProjectMapper projectMapper;
    private final TestSuiteMapper testSuiteMapper;
    private final TestSuiteRepository testSuiteRepository;
    private final EvictService evictService;

    public ExcelConverterServiceImpl(
            ProjectUtil projectUtil,
            TestCaseService testCaseService,
            ExcelConverterServiceUtil excelConverterServiceUtil,
            ProjectMapper projectMapper,
            TestSuiteMapper testSuiteMapper,
            TestSuiteRepository testSuiteRepository,
            EvictService evictService
    ) {
        this.projectUtil = projectUtil;
        this.testCaseService = testCaseService;
        this.excelConverterServiceUtil = excelConverterServiceUtil;
        this.projectMapper = projectMapper;
        this.testSuiteMapper = testSuiteMapper;
        this.testSuiteRepository = testSuiteRepository;
        this.evictService = evictService;
    }

    @Override
    public byte[] convertToExcel(Long projectId) {
        Project project = projectUtil.getProjectById(projectId);

        Map<String, List<TestCaseDTO>> testSuitesWithTestCases = project
                .getTestsSuites()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                TestSuite::getTitle,
                                Collectors.mapping(
                                testSuite -> testCaseService.getTestCasesByTestSuiteId(testSuite.getId()),
                                        Collectors.flatMapping(List::stream, Collectors.toList())
                                )
                        )
                );

        return excelConverterServiceUtil.convertAndWriteToExcel(testSuitesWithTestCases);
    }

    @Override
    @Transactional
    public ExtendedProjectDTO convertFromExcel(InputStream excelFileInputStream, Long projectId) {
        List<TestSuite> testSuites = excelConverterServiceUtil.parseFromExcel(excelFileInputStream, projectId);

        Project project = projectUtil.getProjectById(projectId);

        evictService.evictProjectCache(projectId);

        List<TestSuite> savedTestSuites = testSuiteRepository.saveAll(testSuites);

        List<TestSuiteDTO> mappedTestSuites = savedTestSuites.stream()
                .map(testSuiteMapper::toBaseTestSuiteDTO)
                .toList();

        return new ExtendedProjectDTO(
                project.getTitle(),
                project.getDescription(),
                mappedTestSuites
        );
    }

}
