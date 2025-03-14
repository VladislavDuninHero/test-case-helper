package com.tests.test_case_helper.service.utils.cases;

import com.tests.test_case_helper.dto.cases.CreateTestCaseDTO;
import com.tests.test_case_helper.dto.cases.CreateTestCaseResponseDTO;
import com.tests.test_case_helper.entity.cases.TestCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "testCaseData", target = "testCaseData")
    @Mapping(source = "precondition", target = "testCasePrecondition")
    @Mapping(source = "steps", target = "steps")
    @Mapping(source = "expectedResult", target = "expectedResult")
    TestCase toEntity(CreateTestCaseDTO testCaseResponseDTO);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "testCaseData", target = "testCaseData")
    @Mapping(source = "testCasePrecondition", target = "precondition")
    @Mapping(source = "steps", target = "steps")
    @Mapping(source = "expectedResult", target = "expectedResult")
    CreateTestCaseResponseDTO toDto(TestCase testCase);
}
