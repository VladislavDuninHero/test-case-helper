package com.tests.test_case_helper.controller.cases;

import com.tests.test_case_helper.constants.ResponseMessage;
import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.cases.CreateTestCaseDTO;
import com.tests.test_case_helper.dto.cases.CreateTestCaseResponseDTO;
import com.tests.test_case_helper.dto.cases.TestCaseDTO;
import com.tests.test_case_helper.dto.cases.UpdateTestCaseDTO;
import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.service.cases.TestCaseService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(Route.API_GET_ROUTE)
    @PreAuthorize("hasAuthority('READ_TEST_CASES')")
    @Validated
    public ResponseEntity<TestCaseDTO> getTestCase(
            @PathVariable
            @NotNull
            Long id
    ) {
        TestCaseDTO testCase = testCaseService.getTestCase(id);

        return ResponseEntity.ok(testCase);
    }

    @PutMapping(Route.API_UPDATE_ROUTE)
    @PreAuthorize("hasAuthority('UPDATE_TEST_CASES')")
    @Validated
    public ResponseEntity<TestCaseDTO> updateTestCase(
            @PathVariable
            @NotNull
            Long id,
            @RequestBody
            @Validated
            UpdateTestCaseDTO updateTestCaseDTO
    ) {

        TestCaseDTO testCase = testCaseService.updateTestCase(id, updateTestCaseDTO);

        return ResponseEntity.ok(testCase);
    }

    @DeleteMapping(Route.API_DELETE_ROUTE)
    @PreAuthorize("hasAuthority('DELETE_TEST_CASES')")
    @Validated
    public ResponseEntity<ResponseMessageDTO> deleteTestCase(
            @PathVariable
            @NotNull
            Long id
    ) {

        testCaseService.deleteTestCase(id);

        return ResponseEntity.ok(new ResponseMessageDTO(ResponseMessage.TEST_CASE_SUCCESSFULLY_DELETED));
    }
}
