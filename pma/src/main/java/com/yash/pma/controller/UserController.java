package com.yash.pma.controller;

import com.yash.pma.domain.User;
import com.yash.pma.exception.UserNotFoundException;
import com.yash.pma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/list")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser (@PathVariable Long userId, @RequestBody User user) throws UserNotFoundException {
        user.setUserId(userId);
        User updatedUser  = userService.updateUser (user); // Call the service to update the user
        return ResponseEntity.ok(updatedUser ); // Return the updated user
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
