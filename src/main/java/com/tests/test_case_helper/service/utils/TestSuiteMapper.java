package com.tests.test_case_helper.service.utils;

import com.tests.test_case_helper.dto.suite.CreateTestSuiteDTO;
import com.tests.test_case_helper.dto.suite.CreateTestSuiteResponseDTO;
import com.tests.test_case_helper.dto.suite.TestSuiteDTO;
import com.tests.test_case_helper.entity.TestSuite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestSuiteMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    TestSuite toEntity(CreateTestSuiteDTO testSuite);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "tag", target = "tag")
    CreateTestSuiteResponseDTO toDto(TestSuite testSuite);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "tag", target = "tag")
    TestSuiteDTO toBaseTestSuiteDTO(TestSuite testSuite);
}
