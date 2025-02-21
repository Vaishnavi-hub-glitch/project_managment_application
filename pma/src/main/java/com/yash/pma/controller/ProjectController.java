package com.yash.pma.controller;

import com.yash.pma.domain.Project;
import com.yash.pma.exception.ProjectNotFoundException;
import com.yash.pma.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return projectService.saveProject(project);

    }

    @GetMapping("/list")
    public List<Project> getAllProjects() {
        return projectService.findAllProjects();

    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") Long projectId) throws ProjectNotFoundException {
        Project project = projectService.findProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") Long projectId, @RequestBody Project project) throws ProjectNotFoundException {
        project.setProjectId(projectId); // Ensure the ID is set for the update
        Project updatedProject = projectService.updateProject(project);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long projectId) throws ProjectNotFoundException {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}