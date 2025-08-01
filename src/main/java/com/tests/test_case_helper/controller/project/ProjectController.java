package com.tests.test_case_helper.controller.project;

import com.tests.test_case_helper.constants.Route;
import com.tests.test_case_helper.dto.message.ResponseMessageDTO;
import com.tests.test_case_helper.dto.project.*;
import com.tests.test_case_helper.service.project.ProjectService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('READ_PROJECT')")
    public ResponseEntity<ExtendedProjectDTO> getProject(
            @PathVariable
            @NotNull
            @Positive
            Long id
    ) {
        ExtendedProjectDTO extendedProjectDTO = projectService.getProject(id);

        return ResponseEntity.ok(extendedProjectDTO);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PROJECT')")
    public ResponseEntity<List<ProjectTeamDTO>> getProjects() {
        List<ProjectTeamDTO> projects = projectService.getAllProjects();

        return ResponseEntity.ok(projects);
    }

    @PutMapping(Route.API_UPDATE_ROUTE)
    @PreAuthorize("hasAuthority('UPDATE_PROJECT')")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable
            @NotNull
            @Positive
            Long id,
            @RequestBody
            @Validated
            UpdateProjectDTO projectDTO
    ) {

        ProjectDTO updatedProject = projectService.updateProject(id, projectDTO);

        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping(Route.API_DELETE_ROUTE)
    @PreAuthorize("hasAuthority('DELETE_PROJECT')")
    public ResponseEntity<ResponseMessageDTO> deleteProject(
            @PathVariable
            @NotNull
            @Positive
            Long id
    ) {

        projectService.deleteProject(id);

        return ResponseEntity.ok(new ResponseMessageDTO("Success"));
    }
}
