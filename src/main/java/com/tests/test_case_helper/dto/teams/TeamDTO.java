package com.tests.test_case_helper.dto.teams;

import com.tests.test_case_helper.dto.user.UserDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDTO {
    private Long id;
    private String teamName;
    private Set<UserDTO> teammates;
}
