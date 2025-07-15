package com.tests.test_case_helper.dto.suite.run;

import com.tests.test_case_helper.constants.ExceptionMessage;
import com.tests.test_case_helper.enums.Tag;
import com.tests.test_case_helper.enums.TestCaseStatus;
import com.tests.test_case_helper.service.validation.annotations.EnumValidate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTestCaseResultDTO {

    @NotNull
    @Positive
    private Long testSuiteId;

    @NotNull
    @Positive
    private Long sessionId;

    @NotNull
    @Positive
    private Long testCaseResultId;

    @Size(max = 255)
    private String actualResult;

    @NotNull
    @EnumValidate(enumClass = TestCaseStatus.class, message = ExceptionMessage.TEST_CASE_STATUS_NOT_FOUND_EXCEPTION_MESSAGE)
    private String status;

    @Size(max = 255)
    private String comment;
}
