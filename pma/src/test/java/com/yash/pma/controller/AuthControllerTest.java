package com.yash.pma.controller;

import com.yash.pma.dto.LoginRequest;
import com.yash.pma.security.JwtService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ContextConfiguration(classes = UserController.class)
@ExtendWith(SpringExtension.class)

public class AuthControllerTest {

    @Spy
    private AuthController authController;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private Authentication authentication;

    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        loginRequest.setEmail("demo@yash.com");
        loginRequest.setPassword("demo");

        authController = spy(new AuthController(jwtService, authenticationManager));
    }

    @Test
    public void testLogin_Success() {
        doReturn(authentication).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        doReturn(true).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class)).isAuthenticated();
        doReturn("mocked-jwt-token").when(jwtService).generateToken(loginRequest.getEmail());


        String token = authController.login(loginRequest);

        assertEquals("mocked-jwt-token", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(loginRequest.getEmail());
    }
}
