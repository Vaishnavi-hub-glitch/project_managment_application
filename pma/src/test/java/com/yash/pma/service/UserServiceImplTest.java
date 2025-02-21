package com.yash.pma.service;

import com.yash.pma.domain.User;
import com.yash.pma.exception.UserNotFoundException;
import com.yash.pma.repository.UserRepository;
import com.yash.pma.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = UserServiceImpl.class)
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockitoBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setName("demo");
        user.setEmail("demo@yash.com");
        user.setEmployeeId(1100682L);
        user.setPassword("demo");
    }

    @Test
    public void testSaveUser(){
        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("demo",savedUser.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindAllUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.findAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("demo",users.get(0).getName());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    public void testUpdateUser_Success() throws UserNotFoundException {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser  = userService.updateUser (user);
        assertNotNull(updatedUser );
        assertEquals("demo", updatedUser .getName());
        verify(userRepository, times(1)).findById(user.getUserId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser () {
        // Arrange
        doNothing().when(userRepository).deleteById(user.getUserId());

        // Act
        userService.deleteUser (user.getUserId());

        // Assert
        verify(userRepository, times(1)).deleteById(user.getUserId());
    }
}
