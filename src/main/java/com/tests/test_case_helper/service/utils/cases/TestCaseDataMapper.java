package com.tests.test_case_helper.service.utils.cases;

import com.tests.test_case_helper.dto.cases.data.TestCaseDataDTO;
import com.tests.test_case_helper.entity.cases.TestCaseData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestCaseDataMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "step", target = "step")
    TestCaseData toEntity(TestCaseDataDTO testCaseDataDTO);

    @Mapping(source = "step", target = "step")
    TestCaseDataDTO toDto(TestCaseData testCaseData);
}
