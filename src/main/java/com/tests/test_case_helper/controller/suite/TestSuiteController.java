package com.tests.test_case_helper.controller.suite;

import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.suite.CreateTestSuiteDTO;
import com.tests.test_case_helper.dto.suite.CreateTestSuiteResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.API_TEST_SUITE_ROUTE)
public class TestSuiteController {

    @PostMapping(Route.API_CREATE_ROUTE)
    public ResponseEntity<CreateTestSuiteResponseDTO> createTestSuite(
            @Validated
            @RequestBody
            CreateTestSuiteDTO createTestSuiteDTO
    ) {



        return ResponseEntity.ok(new CreateTestSuiteResponseDTO());
    }
}
