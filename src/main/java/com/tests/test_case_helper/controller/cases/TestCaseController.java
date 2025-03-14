package com.tests.test_case_helper.controller.cases;

import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.cases.CreateTestCaseDTO;
import com.tests.test_case_helper.dto.cases.CreateTestCaseResponseDTO;
import com.tests.test_case_helper.service.cases.TestCaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.API_TEST_CASE_ROUTE)
public class TestCaseController {

    private final TestCaseService testCaseService;

    public TestCaseController(final TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @PostMapping(Route.API_CREATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEST_CASES')")
    public ResponseEntity<CreateTestCaseResponseDTO> createTestCase(
            @RequestBody
            @Validated
            CreateTestCaseDTO createTestCaseDTO
    ) {

        CreateTestCaseResponseDTO createTestCaseResponseDTO = testCaseService.create(createTestCaseDTO);

        return ResponseEntity.ok(createTestCaseResponseDTO);
    }
}
