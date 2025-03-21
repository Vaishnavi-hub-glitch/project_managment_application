package com.yash.pma.serviceimpl;

import com.yash.pma.domain.User;
import com.yash.pma.exception.UserNotFoundException;
import com.yash.pma.repository.UserRepository;
import com.yash.pma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {

        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + user.getUserId()));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setEmployeeId(user.getEmployeeId());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
         userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
