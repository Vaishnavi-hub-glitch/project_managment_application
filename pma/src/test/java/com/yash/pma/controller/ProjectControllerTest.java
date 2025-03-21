package com.yash.pma.controller;

import com.yash.pma.domain.Project;
import com.yash.pma.exception.ProjectNotFoundException;
import com.yash.pma.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ContextConfiguration(classes = ProjectController.class)
@ExtendWith(SpringExtension.class)
public class ProjectControllerTest {

    @Autowired
    private ProjectController projectController;

    @MockitoBean
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Test Project");

        when(projectService.saveProject(any(Project.class))).thenReturn(project);

        ResponseEntity<?> createdProject = projectController.createProject(project);

        assertNotNull(createdProject);
        assertEquals("Test Project", createdProject.getBody());
        verify(projectService, times(1)).saveProject(any(Project.class));
    }

    @Test
    public void testGetAllProjects() {
        Project project1 = new Project();
        project1.setProjectId(1L);
        project1.setProjectName("Project 1");

        Project project2 = new Project();
        project2.setProjectId(2L);
        project2.setProjectName("Project 2");

        List<Project> projects = Arrays.asList(project1, project2);
        when(projectService.findAllProjects()).thenReturn(projects);

        List<Project> result = projectController.getAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectService, times(1)).findAllProjects();
    }

    @Test
    public void testGetProjectById() throws ProjectNotFoundException {
        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Test Project");

        when(projectService.findProjectById(1L)).thenReturn(project);

        ResponseEntity<Project> response = projectController.getProjectById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Project", response.getBody().getProjectName());
        verify(projectService, times(1)).findProjectById(1L);
    }

    @Test
    public void testUpdateProject() throws ProjectNotFoundException {
        Project existingProject = new Project();
        existingProject.setProjectId(1L);
        existingProject.setProjectName("Existing Project");

        Project updatedProject = new Project();
        updatedProject.setProjectId(1L);
        updatedProject.setProjectName("Updated Project");

        when(projectService.updateProject(any(Project.class))).thenReturn(updatedProject);
        when(projectService.findProjectById(1L)).thenReturn(existingProject);

        ResponseEntity<Project> response = projectController.updateProject(1L, updatedProject);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Project", response.getBody().getProjectName());
        verify(projectService, times(1)).updateProject(any(Project.class));
    }

    @Test
    public void testDeleteProject() throws ProjectNotFoundException {
        doNothing().when(projectService).deleteProject(1L);

        ResponseEntity<Void> response = projectController.deleteProject(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectService, times(1)).deleteProject(1L);
    }

    @Test
    public void testGetProjectByIdNotFound() throws ProjectNotFoundException {
        when(projectService.findProjectById(1L)).thenThrow(new ProjectNotFoundException("Project not found"));

        assertThrows(ProjectNotFoundException.class, () -> {
            projectController.getProjectById(1L);
        });
    }
}