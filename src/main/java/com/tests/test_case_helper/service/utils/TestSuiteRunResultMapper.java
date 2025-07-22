package com.tests.test_case_helper.service.utils;

import com.tests.test_case_helper.dto.suite.run.cases.TestCaseRunResultDTO;
import com.tests.test_case_helper.dto.suite.run.TestSuiteRunSessionStatisticDTO;
import com.tests.test_case_helper.entity.TestCaseRunResult;
import com.tests.test_case_helper.repository.TestCaseRunResultsRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestSuiteRunResultMapper {
    @Mapping(target = "testCase", source = "testCase")
    @Mapping(source = "actualResult", target = "actualResult")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "comment", target = "comment")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TestCaseRunResult toEntity(TestCaseRunResultDTO testSuiteRunResult);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "testCase", target = "testCase")
    @Mapping(source = "actualResult", target = "actualResult")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "comment", target = "comment")
    TestCaseRunResultDTO toDto(TestCaseRunResult testSuiteRunResult);

    @Mapping(source = "status", target = "status")
    @Mapping(source = "count", target = "count")
    TestSuiteRunSessionStatisticDTO mapStatisticToDto(
            TestCaseRunResultsRepository.StatusStatisticProjection suiteRunSessionStatisticDTO
    );
}
