package com.tests.test_case_helper.dto.teams;

import com.tests.test_case_helper.dto.user.UserDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeammatesDTO {
    private Long teamId;
    private Set<UserDTO> teammates;
}
