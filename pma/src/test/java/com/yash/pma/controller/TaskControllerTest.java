
package com.yash.pma.controller;

import com.yash.pma.domain.Task;
import com.yash.pma.exception.TaskNotFoundException;
import com.yash.pma.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ContextConfiguration(classes = TaskController.class)
@ExtendWith(SpringExtension.class)
class TaskControllerTest {

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private TaskController taskController;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setTaskId(1);

    }

    @Test
    void testCreateTask() {
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        ResponseEntity<Task> response = taskController.createTask(task);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(task, response.getBody());
    }

    @Test
    void testGetTaskById() {
        when(taskService.getTaskById(1)).thenReturn(Optional.of(task));

        ResponseEntity<Task> response = taskController.getTaskById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(task, response.getBody());
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(taskService.getTaskById(1)).thenReturn(Optional.empty());

        ResponseEntity<Task> response = taskController.getTaskById(1);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.getAllTasks()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tasks, response.getBody());
    }

    @Test
    void testUpdateTask() throws TaskNotFoundException {
        when(taskService.getTaskById(1)).thenReturn(Optional.of(task));
        when(taskService.updateTask(any(Task.class))).thenReturn(task);

        ResponseEntity<Task> response = taskController.updateTask(1, task);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(task, response.getBody());
    }

    @Test
    void testUpdateTaskNotFound() throws TaskNotFoundException {
        when(taskService.getTaskById(1)).thenReturn(Optional.empty());

        ResponseEntity<Task> response = taskController.updateTask(1, task);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTask() {
        when(taskService.getTaskById(1)).thenReturn(Optional.of(task));
        doNothing().when(taskService).deleteTask(1);

        ResponseEntity<Void> response = taskController.deleteTask(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(taskService, times(1)).deleteTask(1);
    }

    @Test
    void testDeleteTaskNotFound() {
        when(taskService.getTaskById(1)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = taskController.deleteTask(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}
