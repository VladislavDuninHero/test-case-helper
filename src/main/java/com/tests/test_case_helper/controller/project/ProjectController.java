package com.tests.test_case_helper.controller.project;

import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.project.CreateProjectDTO;
import com.tests.test_case_helper.dto.project.CreateProjectResponseDTO;
import com.tests.test_case_helper.dto.project.ProjectDTO;
import com.tests.test_case_helper.service.project.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Route.API_PROJECT_ROUTE)
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(Route.API_CREATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_PROJECT')")
    public ResponseEntity<CreateProjectResponseDTO> createProject(
            @RequestBody
            @Validated
            CreateProjectDTO createProjectDTO
    ) {

        CreateProjectResponseDTO project = projectService.createProject(createProjectDTO);

        return ResponseEntity.ok(project);
    }

    @GetMapping(Route.API_GET_PROJECT_ROUTE)
    public ResponseEntity<ProjectDTO> getProject(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(null);
    }
}
