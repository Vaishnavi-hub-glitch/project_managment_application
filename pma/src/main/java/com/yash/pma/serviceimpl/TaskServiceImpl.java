package com.yash.pma.serviceimpl;

import com.yash.pma.domain.Project;
import com.yash.pma.domain.Task;
import com.yash.pma.domain.User;
import com.yash.pma.exception.TaskNotFoundException;
import com.yash.pma.repository.ProjectRepository;
import com.yash.pma.repository.TaskRepository;
import com.yash.pma.repository.UserRepository;
import com.yash.pma.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(int taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task updateTask(Task task) throws TaskNotFoundException {
        Task existingTask = taskRepository.findById(task.getTaskId())
                .orElseThrow(()->new TaskNotFoundException ("task not found "+task.getTaskId()));

        existingTask.setTaskId(task.getTaskId());
        existingTask.setName(task.getName());
        existingTask.setTaskPriority(task.getTaskPriority());
        existingTask.setStartDate(task.getStartDate());
        existingTask.setEndDate(task.getEndDate());
        List<Integer> userList = task.getUserList();
        if(!userList.isEmpty()) {
            for (Integer userId : userList) {
                Optional<User> user = userRepository.findById(Long.valueOf(userId));
                user.ifPresent(userObj -> {
                    List<Integer> taskList = userObj.getTasks();
                    if (taskList == null || taskList.isEmpty()) {
                        taskList = new ArrayList<>();
                        taskList.add(task.getTaskId());
                        userObj.setTasks(taskList);
                    } else {
                        taskList.add(task.getTaskId());
                    }
                });
            }
        }

        existingTask.setUserList(userList);
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(int taskId) {
        taskRepository.deleteById(taskId);
    }
}
