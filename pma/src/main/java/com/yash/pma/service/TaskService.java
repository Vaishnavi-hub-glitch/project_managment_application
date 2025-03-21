package com.yash.pma.service;

import com.yash.pma.domain.Task;
import com.yash.pma.exception.TaskNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    Optional<Task> getTaskById(int taskId);
    List<Task> getAllTasks();
    Task updateTask(Task task) throws TaskNotFoundException;
    void deleteTask(int taskId);
}
