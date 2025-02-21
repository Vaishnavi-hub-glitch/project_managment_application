package com.yash.pma.controller;

import com.yash.pma.domain.User;
import com.yash.pma.exception.UserNotFoundException;
import com.yash.pma.service.UserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = UserController.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockitoBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setName("demo ");
        user.setEmail("demo@yash.com");
    }

    @Test
    public void testCreateUser(){
        when(userService.saveUser(any(User.class))).thenReturn(user);

        User createUser  = userController.createUser(user);
        assertNotNull(createUser );
        assertEquals("demo ",createUser.getName());
        verify(userService, times(1)).saveUser (any(User.class));
    }

    @Test
   public void testGetAllUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findAllUsers()).thenReturn(userList);

        List<User> users = userController.getAllUsers();
        assertNotNull(users);
        assertEquals(1,users.size());
        assertEquals("demo ",users.get(0).getName());
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void testUpdateUser() throws UserNotFoundException {
        when(userService.updateUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.updateUser(1L, user);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("demo ", responseEntity.getBody().getName());
        verify(userService, times(1)).updateUser(any(User.class));

    }

    @Test
    public void testDeleteUser () {
        doNothing().when(userService).deleteUser (1L);

        ResponseEntity<Void> responseEntity = userController.deleteUser (1L);

        assertNotNull(responseEntity);
        assertEquals(204, responseEntity.getStatusCodeValue());
        verify(userService, times(1)).deleteUser (1L);
    }
}
