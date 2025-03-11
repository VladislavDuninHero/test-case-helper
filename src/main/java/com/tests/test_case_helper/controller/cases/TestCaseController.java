package com.tests.test_case_helper.controller.cases;

import com.tests.test_case_helper.constants.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Route.API_TEST_CASE_ROUTE)
public class TestCaseController {

    @PostMapping(Route.API_CREATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_TEST_CASES')")
    public ResponseEntity<String> createTestCase() {



        return ResponseEntity.ok("");
    }
}
