package com.yash.pma.controller;

import com.yash.pma.domain.User;
import com.yash.pma.dto.LoginRequest;
import com.yash.pma.dto.RoleRequest;
import com.yash.pma.security.JwtService;
import com.yash.pma.util.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<RoleRequest> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            RoleRequest roleRequest;
            if (authentication.isAuthenticated()) {
                roleRequest = new RoleRequest();
                roleRequest.setEmail(loginRequest.getEmail());
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
                roleRequest.setRole(((User)userDetails).getUserRole());
                roleRequest.setJwtToken(jwtService.generateToken(loginRequest.getEmail()));
                if (((User) userDetails).getUserRole() == Role.DEVELOPER) {
                    roleRequest.setProjectId(((User) userDetails).getProjects());
                }
                return ResponseEntity.ok(roleRequest);
            }
            return (ResponseEntity<RoleRequest>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return (ResponseEntity<RoleRequest>) ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
    }

}
