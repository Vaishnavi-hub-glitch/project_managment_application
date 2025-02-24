package com.yash.pma.serviceimpl;

import com.yash.pma.domain.Project;
import com.yash.pma.exception.ProjectNotFoundException; // Create this exception class
import com.yash.pma.repository.ProjectRepository;
import com.yash.pma.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project findProjectById(Long projectId) throws ProjectNotFoundException {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + projectId));
    }

    @Override
    public Project updateProject(Project project) throws ProjectNotFoundException {
        // Check if the project exists
        Project existingProject = projectRepository.findById(project.getProjectId())
                .orElseThrow(()->new ProjectNotFoundException("project not found with ID: "+ project.getProjectId()));
        existingProject.setProjectName(project.getProjectName());
        existingProject.setProjectDescription(project.getProjectDescription());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setEndDate(project.getEndDate());
        return projectRepository.save(project);
        }

    @Override
    public void deleteProject(Long projectId) throws ProjectNotFoundException {

        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }
        projectRepository.deleteById(projectId);

    }

}

