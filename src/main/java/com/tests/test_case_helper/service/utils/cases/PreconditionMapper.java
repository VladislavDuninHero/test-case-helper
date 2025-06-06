package com.tests.test_case_helper.service.utils.cases;

import com.tests.test_case_helper.dto.cases.precondition.PreconditionDTO;
import com.tests.test_case_helper.entity.cases.TestCasePrecondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PreconditionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "step", target = "step")
    TestCasePrecondition toEntity(PreconditionDTO preconditionDTO);

    @Mapping(source = "step", target = "step")
    PreconditionDTO toDto(TestCasePrecondition preconditionDTO);
}
