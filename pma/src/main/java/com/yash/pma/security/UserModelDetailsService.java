package com.yash.pma.security;

import com.yash.pma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserModelDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserModelDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).stream()
                .filter(user -> user.getUsername().equals(email)).findAny().orElseThrow(()->new UsernameNotFoundException(email + "not exists"));
    }
}
