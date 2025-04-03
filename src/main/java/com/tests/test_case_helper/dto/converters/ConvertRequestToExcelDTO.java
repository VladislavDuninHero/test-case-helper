package com.tests.test_case_helper.dto.converters;

import com.tests.test_case_helper.dto.suite.ExtendedTestSuiteDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConvertRequestToExcelDTO {
    private ExtendedTestSuiteDTO extendedTestSuiteDTO;
}
