package com.tests.test_case_helper.dto.suite;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.enums.Tag;
import com.tests.test_case_helper.service.validation.annotations.EnumValidate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTestSuiteDTO {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 200)
    private String description;

    @EnumValidate(enumClass = Tag.class, message = ExceptionMessage.TAG_NOT_FOUND_EXCEPTION_MESSAGE)
    private String tag = Tag.NOT_ASSIGNED.name();
}
