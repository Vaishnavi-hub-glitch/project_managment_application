package com.yash.pma.serviceimpl;

import com.yash.pma.domain.Project;
import com.yash.pma.domain.User;
import com.yash.pma.exception.ProjectNotFoundException; // Create this exception class
import com.yash.pma.repository.ProjectRepository;
import com.yash.pma.repository.UserRepository;
import com.yash.pma.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

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
        List<Integer> userList = project.getUserList();
        List<Integer> existingUserList = existingProject.getUserList();
        if(userList != null) {
            for (Integer user : userList) {
                Optional<User> userObj = userRepository.findById(Long.valueOf(user));
//            userObj.ifPresent(userId -> {
//                if (userId.getProjects() != null) {
//                    throw new RuntimeException("User is Already in project");
//                }
//            });

                userObj.ifPresent(userId -> userId.setProjects(Math.toIntExact(project.getProjectId())));
            }
        }
        existingProject.setUserList(userList);
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

