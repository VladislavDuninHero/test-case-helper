package com.tests.test_case_helper.service.utils;

import com.tests.test_case_helper.dto.suite.run.TestCaseRunResultDTO;
import com.tests.test_case_helper.entity.TestCaseRunResult;
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

    @Mapping(source = "testCase", target = "testCase")
    @Mapping(source = "actualResult", target = "actualResult")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "comment", target = "comment")
    TestCaseRunResultDTO toDto(TestCaseRunResult testSuiteRunResult);
}
