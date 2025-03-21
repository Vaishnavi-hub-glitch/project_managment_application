package com.yash.pma.service;

import com.yash.pma.domain.Task;
import com.yash.pma.domain.User;
import com.yash.pma.exception.TaskNotFoundException;
import com.yash.pma.repository.ProjectRepository;
import com.yash.pma.repository.TaskRepository;
import com.yash.pma.repository.UserRepository;
import com.yash.pma.service.TaskService;
import com.yash.pma.serviceimpl.TaskServiceImpl;
import com.yash.pma.util.Priority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TaskServiceImpl.class)
@ExtendWith(SpringExtension.class)
class TaskServiceImplTest {

    @MockitoBean
    private TaskRepository taskRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ProjectRepository projectRepository;

    @Autowired
    private TaskServiceImpl taskService;

    private Task task;
    private User user;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setTaskId(1);
        task.setTaskPriority(Priority.HIGH);
        SimpleDateFormat smp = new SimpleDateFormat("DD-MM-YYYY");
        task.setStartDate(smp.parse("01-01-2025"));
        task.setEndDate(smp.parse("01-03-2025"));
        task.setUserList(Arrays.asList(1));

        user = new User();
        user.setUserId(1L);
        user.setTasks(Arrays.asList(1));
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals(1, createdTask.getTaskId());
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById(1);

        assertTrue(foundTask.isPresent());
        assertEquals(1, foundTask.get().getTaskId());
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> allTasks = taskService.getAllTasks();

        assertNotNull(allTasks);
        assertEquals(1, allTasks.size());
    }

    @Test
    void testUpdateTask() throws TaskNotFoundException {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Task updatedTask = taskService.updateTask(task);

        assertNotNull(updatedTask);
        assertEquals(1, updatedTask.getTaskId());
    }

    @Test
    void testUpdateTaskNotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(task);
        });
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1);

        taskService.deleteTask(1);

        verify(taskRepository, times(1)).deleteById(1);
    }
}
