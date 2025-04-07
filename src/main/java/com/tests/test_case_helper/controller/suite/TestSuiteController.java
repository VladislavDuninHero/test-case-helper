package com.tests.test_case_helper.controller.suite;

import com.tests.test_case_helper.constants.ResponseMessage;
import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.suite.*;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(Route.API_GET_TEST_SUITE_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    public ResponseEntity<ExtendedTestSuiteDTO> getTestSuite(
            @PathVariable
            @NotNull
            @Positive
            Long id
    ) {

        ExtendedTestSuiteDTO extendedTestSuiteDTO = testSuiteService.getTestSuite(id);

        return ResponseEntity.ok(extendedTestSuiteDTO);
    }

    @PutMapping(Route.API_UPDATE_ROUTE)
    @PreAuthorize("hasAuthority('UPDATE_TEST_SUITE')")
    public ResponseEntity<TestSuiteDTO> updateTestSuite(
            @PathVariable
            @NotNull
            Long id,
            @RequestBody
            @Validated
            UpdateTestSuiteDTO updateTestSuiteDTO
    ) {

        TestSuiteDTO updatedTestSuite = testSuiteService.updateTestSuiteById(id, updateTestSuiteDTO);

        return ResponseEntity.ok(updatedTestSuite);
    }

    @DeleteMapping(Route.API_DELETE_ROUTE)
    @PreAuthorize("hasAuthority('DELETE_TEST_SUITE')")
    public ResponseEntity<ResponseMessage> deleteTestSuite(
            @PathVariable
            @NotNull
            Long id
    ) {



        return ResponseEntity.noContent().build();
    }
}
