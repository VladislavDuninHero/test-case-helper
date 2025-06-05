package com.tests.test_case_helper.controller.suite;

import com.tests.test_case_helper.constants.ResponseMessage;
import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.dto.suite.*;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createTestSuiteResponseDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(createTestSuiteResponseDTO);
    }

    @GetMapping(Route.API_GET_TEST_SUITE_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    @Validated
    public ResponseEntity<ExtendedTestSuiteDTO> getTestSuite(
            @PathVariable
            @NotNull
            @Positive
            Long id,
            Pageable pageable
    ) {
        ExtendedTestSuiteDTO extendedTestSuiteDTO = testSuiteService.getTestSuite(id, pageable);

        return ResponseEntity.ok(extendedTestSuiteDTO);
    }

    @GetMapping(Route.API_GET_SLIM_TEST_SUITE_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    @Validated
    public ResponseEntity<TestSuiteDTO> getSlimTestSuite(
            @PathVariable
            @NotNull
            Long id
    ) {
        TestSuiteDTO testSuiteDTO = testSuiteService.getSlimTestSuite(id);

        return ResponseEntity.ok(testSuiteDTO);
    }

    @PutMapping(Route.API_UPDATE_ROUTE)
    @PreAuthorize("hasAuthority('UPDATE_TEST_SUITE')")
    @Validated
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
    @Validated
    public ResponseEntity<ResponseMessageDTO> deleteTestSuite(
            @PathVariable
            @NotNull
            Long id
    ) {

        testSuiteService.deleteTestSuite(id);

        return ResponseEntity.ok(new ResponseMessageDTO(ResponseMessage.TEST_SUITE_SUCCESSFULLY_DELETED));
    }

    @PostMapping(Route.API_RUN_TEST_SUITE_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    @Validated
    public ResponseEntity<ExtendedTestSuiteDTO> runTestSuite(
            @PathVariable
            @NotNull
            Long id
    ) {
        ExtendedTestSuiteDTO runTestSuite = testSuiteService.runTestSuite(id);

        return ResponseEntity.ok(runTestSuite);
    }
}
