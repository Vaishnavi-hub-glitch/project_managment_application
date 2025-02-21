package com.yash.pma.service;

import com.yash.pma.domain.User;
import com.yash.pma.exception.UserNotFoundException;

import java.util.List;

public interface UserService {


    User saveUser (User user);
    List<User> findAllUsers();
    User updateUser (User user) throws UserNotFoundException;
    void deleteUser (Long userId);


}
