package com.tests.test_case_helper.service.converter.impl;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.entity.Project;
import com.tests.test_case_helper.entity.TestSuite;
import com.tests.test_case_helper.service.cases.TestCaseService;
import com.tests.test_case_helper.service.converter.ExcelConverterService;
import com.tests.test_case_helper.service.converter.util.ExcelConverterServiceUtil;
import com.tests.test_case_helper.service.project.utils.impl.ProjectUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelConverterServiceImpl implements ExcelConverterService {

    private final ProjectUtil projectUtil;
    private final TestCaseService testCaseService;
    private final ExcelConverterServiceUtil excelConverterServiceUtil;

    public ExcelConverterServiceImpl(
            ProjectUtil projectUtil,
            TestCaseService testCaseService,
            ExcelConverterServiceUtil excelConverterServiceUtil
    ) {
        this.projectUtil = projectUtil;
        this.testCaseService = testCaseService;
        this.excelConverterServiceUtil = excelConverterServiceUtil;
    }

    @Override
    public byte[] convertToExcel(Long projectId) {
        Project project = projectUtil.getProjectById(projectId);

        Map<String, List<TestCaseDTO>> testSuitesWithTestCases = project
                .getTestsSuites()
                .stream()
                .collect(
                        Collectors.toMap(
                                TestSuite::getTitle,
                        testSuite -> testCaseService.getTestCasesByTestSuiteId(testSuite.getId())
                        )
                );

        return excelConverterServiceUtil.convertAndWriteToExcel(testSuitesWithTestCases);
    }

}
