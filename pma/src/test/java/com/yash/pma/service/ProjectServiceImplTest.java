package com.yash.pma.service;

import com.yash.pma.domain.Project;
import com.yash.pma.exception.ProjectNotFoundException;
import com.yash.pma.repository.ProjectRepository;
import com.yash.pma.serviceimpl.ProjectServiceImpl;
import com.yash.pma.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = ProjectServiceImpl.class)
@ExtendWith(SpringExtension.class)
public class ProjectServiceImplTest {

    @Autowired
    private ProjectServiceImpl projectService;

    @MockitoBean
    private ProjectRepository projectRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProject() {
        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Test Project");

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project savedProject = projectService.saveProject(project);

        assertNotNull(savedProject);
        assertEquals("Test Project", savedProject.getProjectName());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void testFindAllProjects() {
        Project project1 = new Project();
        project1.setProjectId(1L);
        project1.setProjectName("Project 1");

        Project project2 = new Project();
        project2.setProjectId(2L);
        project2.setProjectName("Project 2");

        List<Project> projects = Arrays.asList(project1, project2);
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.findAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void testFindProjectById() throws ProjectNotFoundException {
        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Test Project");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project foundProject = projectService.findProjectById(1L);

        assertNotNull(foundProject);
        assertEquals("Test Project", foundProject.getProjectName());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindProjectByIdNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> {
            projectService.findProjectById(1L);
        });
    }

    @Test
    public void testUpdateProject() throws ProjectNotFoundException {
        Project existingProject = new Project();
        existingProject.setProjectId(1L);
        existingProject.setProjectName("Existing Project");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any(Project.class))).thenReturn(existingProject);

        Project updatedProject = projectService.updateProject(existingProject);

        assertNotNull(updatedProject);
        assertEquals("Existing Project", updatedProject.getProjectName());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void testUpdateProjectNotFound() {
        Project project = new Project();
        project.setProjectId(1L);
        project.setProjectName("Non-existing Project");

        when(projectRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProjectNotFoundException.class, () -> {
            projectService.updateProject(project);
        });
    }

    @Test
    public void testDeleteProject() throws ProjectNotFoundException {
        when(projectRepository.existsById(1L)).thenReturn(true);
        doNothing().when(projectRepository).deleteById(1L);

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProjectNotFound() {
        when(projectRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProjectNotFoundException.class, () -> {
            projectService.deleteProject(1L);
        });
    }
}