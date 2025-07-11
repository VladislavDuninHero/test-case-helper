package com.tests.test_case_helper.service.utils;

import com.tests.test_case_helper.dto.suite.run.RunTestSuiteResponseDTO;
import com.tests.test_case_helper.entity.TestSuiteRunSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestSuiteRunSessionMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(source = "testSuite", target = "testSuite")
    @Mapping(source = "executedBy", target = "executedBy")
    @Mapping(source = "environment", target = "environment")
    @Mapping(source = "testCaseRunResults", target = "testCaseRunResults")
    @Mapping(source = "status", target = "status")
    TestSuiteRunSession toEntity(RunTestSuiteResponseDTO testSuiteRunSession);

    @Mapping(target = "id", source = "id")
    @Mapping(source = "testSuite", target = "testSuite")
    @Mapping(source = "executedBy", target = "executedBy")
    @Mapping(source = "environment", target = "environment")
    @Mapping(source = "testCaseRunResults", target = "testCaseRunResults")
    @Mapping(source = "status", target = "status")
    RunTestSuiteResponseDTO toDto(TestSuiteRunSession testSuiteRunSession);
}
