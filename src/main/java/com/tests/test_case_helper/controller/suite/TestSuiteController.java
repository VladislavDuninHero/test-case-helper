package com.tests.test_case_helper.controller.suite;

import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.suite.CreateTestSuiteDTO;
import com.tests.test_case_helper.dto.suite.CreateTestSuiteResponseDTO;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.API_TEST_SUITE_ROUTE)
public class TestSuiteController {

    private final TestSuiteService testSuiteService;

    public TestSuiteController(final TestSuiteService testSuiteService) {
        this.testSuiteService = testSuiteService;
    }

    @PostMapping(Route.API_CREATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEST_SUITE')")
    public ResponseEntity<CreateTestSuiteResponseDTO> createTestSuite(
            @Validated
            @RequestBody
            CreateTestSuiteDTO createTestSuiteDTO
    ) {

        CreateTestSuiteResponseDTO createTestSuiteResponseDTO = testSuiteService.createTestSuite(createTestSuiteDTO);

        return ResponseEntity.ok(createTestSuiteResponseDTO);
    }
}
