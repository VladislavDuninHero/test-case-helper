package com.tests.test_case_helper.dto.suite.run;

import com.tests.test_case_helper.dto.user.UserDTO;
import com.tests.test_case_helper.dto.user.UserFullInfoDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndTestSuiteRunSessionDTO {
    private Long id;
    private String testSuiteTitle;
    private UserDTO executedBy;
    private String environment;
    private Long executionTime;
    private List<TestSuiteRunSessionStatisticDTO> sessionStatistic;
}
