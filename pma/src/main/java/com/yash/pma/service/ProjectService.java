package com.yash.pma.service;

import com.yash.pma.domain.Project;
import com.yash.pma.exception.ProjectNotFoundException;

import java.util.List;

public interface ProjectService {
    Project saveProject(Project project);
    List<Project> findAllProjects();
    Project findProjectById(Long projectId) throws ProjectNotFoundException;
    Project updateProject(Project project) throws ProjectNotFoundException;
    void deleteProject(Long projectId) throws ProjectNotFoundException;
}