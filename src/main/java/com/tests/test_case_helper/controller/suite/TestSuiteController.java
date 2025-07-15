package com.tests.test_case_helper.controller.suite;

import com.tests.test_case_helper.constants.ResponseMessage;
import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.dto.suite.*;
import com.tests.test_case_helper.dto.suite.run.*;
import com.tests.test_case_helper.enums.Environment;
import com.tests.test_case_helper.service.suite.TestSuiteService;
import com.tests.test_case_helper.service.validation.annotations.EnumValidate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<RunTestSuiteSessionResponseDTO> runTestSuite(
            @PathVariable
            @NotNull
            Long id,
            @RequestParam
            @NotNull
            @EnumValidate(enumClass = Environment.class, message = "")
            String env
    ) {
        RunTestSuiteSessionResponseDTO runTestSuite = testSuiteService.runTestSuite(id, env);

        return ResponseEntity.ok(runTestSuite);
    }

    @GetMapping(Route.API_RUN_TEST_SUITE_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    @Validated
    public ResponseEntity<RunTestSuiteResponseDTO> getRunTestSuiteSessionById(
            @PathVariable
            @NotNull
            Long id,
            @RequestParam
            @NotNull
            Long sessionId,
            Pageable pageable
    ) {
        RunTestSuiteResponseDTO runTestSuite = testSuiteService.getRunTestSuiteSessionById(id, sessionId, pageable);

        return ResponseEntity.ok(runTestSuite);
    }

    @GetMapping(Route.API_GET_ACTIVE_TEST_SUITE_RUN_SESSION_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    @Validated
    public ResponseEntity<List<RunTestSuiteSessionDTO>> getActiveRunTestSuiteSessionById() {
        List<RunTestSuiteSessionDTO> activeRunTestSuiteSessions = testSuiteService.getActiveRunTestSuiteSessions();

        return ResponseEntity.ok(activeRunTestSuiteSessions);
    }

    @DeleteMapping(Route.API_DELETE_RUN_TEST_SUITE_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    @Validated
    public ResponseEntity<ResponseMessageDTO> deleteRunTestSuiteSessionById(
            @PathVariable
            @NotNull
            Long id,
            @PathVariable
            @NotNull
            Long sessionId
    ) {
        ResponseMessageDTO runTestSuite = testSuiteService.deleteRunTestSuiteSessionById(id, sessionId);

        return ResponseEntity.ok(runTestSuite);
    }

    @PutMapping(Route.API_UPDATE_RUN_TEST_SUITE_TC_RESULT_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    @Validated
    public ResponseEntity<UpdateTestCaseResultResponseDTO> updateTestCaseResultInTestSuiteSessionById(
            @RequestBody
            @NotNull
            @Valid
            UpdateTestCaseResultDTO updateTestCaseResultDTO
    ) {
        System.out.println(updateTestCaseResultDTO.getStatus());
        UpdateTestCaseResultResponseDTO testCaseResult = testSuiteService
                .updateTestCaseResultInTestSuiteSessionById(updateTestCaseResultDTO);
        return ResponseEntity.ok(testCaseResult);
    }

    @GetMapping(Route.API_GET_RUN_TEST_SUITE_TC_RESULT_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_SUITE')")
    @Validated
    public ResponseEntity<TestCaseRunResultDTO> getTestCaseResultInTestSuiteById(
            @PathVariable
            @NotNull
            @Positive
            Long id
    ) {
        TestCaseRunResultDTO testCaseResult = testSuiteService.getTestCaseResultInTestSuiteById(id);

        return ResponseEntity.ok(testCaseResult);
    }
}
