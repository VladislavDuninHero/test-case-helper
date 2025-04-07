package com.tests.test_case_helper.service.utils;

import com.tests.test_case_helper.dto.suite.CreateTestSuiteDTO;
import com.tests.test_case_helper.dto.suite.CreateTestSuiteResponseDTO;
import com.tests.test_case_helper.dto.suite.TestSuiteDTO;
import com.tests.test_case_helper.entity.TestSuite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestSuiteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    TestSuite toEntity(CreateTestSuiteDTO testSuite);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "tag", target = "tag")
    @Mapping(target = "projectId", ignore = true)
    CreateTestSuiteResponseDTO toDto(TestSuite testSuite);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "tag", target = "tag")
    TestSuiteDTO toBaseTestSuiteDTO(TestSuite testSuite);
}
