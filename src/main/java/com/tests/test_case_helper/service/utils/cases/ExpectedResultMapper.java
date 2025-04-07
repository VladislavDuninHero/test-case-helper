package com.tests.test_case_helper.service.utils.cases;

import com.tests.test_case_helper.dto.cases.expected.ExpectedResultDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import com.tests.test_case_helper.entity.cases.TestCaseExpectedResult;
import com.tests.test_case_helper.entity.cases.TestCaseStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpectedResultMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "step", target = "step")
    TestCaseExpectedResult toEntity(ExpectedResultDTO expectedResultDTO);

    @Mapping(source = "step", target = "step")
    ExpectedResultDTO toDto(TestCaseExpectedResult preconditionDTO);
}
