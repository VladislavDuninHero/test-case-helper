package com.tests.test_case_helper.service.utils.cases;

import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.dto.cases.steps.StepDTO;
import com.tests.test_case_helper.entity.cases.TestCasePrecondition;
import com.tests.test_case_helper.entity.cases.TestCaseStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StepMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "step", target = "step")
    TestCaseStep toEntity(StepDTO stepDTO);

    @Mapping(source = "step", target = "step")
    StepDTO toDto(TestCaseStep preconditionDTO);
}
