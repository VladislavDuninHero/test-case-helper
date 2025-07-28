package com.tests.test_case_helper.dto.user;

import com.tests.test_case_helper.dto.teams.TeamSlimDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFullInfoDTO {
    private Long id;
    private String login;
    private String email;
    private boolean enabled;
    private Set<TeamSlimDTO> teams;
}
