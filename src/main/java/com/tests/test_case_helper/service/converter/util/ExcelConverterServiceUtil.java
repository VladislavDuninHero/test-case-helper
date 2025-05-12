package com.tests.test_case_helper.service.converter.util;

import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.entity.TestSuite;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ExcelConverterServiceUtil {
    byte[] convertAndWriteToExcel(Map<String, List<TestCaseDTO>> testSuitesAndTestCases);
    List<TestSuite> parseFromExcel(InputStream excelInputStream, Long projectId);
}
