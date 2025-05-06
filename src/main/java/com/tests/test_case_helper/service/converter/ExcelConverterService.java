package com.tests.test_case_helper.service.converter;


import com.tests.test_case_helper.dto.project.ExtendedProjectDTO;

import java.io.InputStream;

public interface ExcelConverterService {
    byte[] convertToExcel(Long projectId);
    ExtendedProjectDTO convertFromExcel(InputStream excelFileInputStream, Long projectId);
}
