package com.personal.service;

import com.personal.model.UserInfo;
import com.personal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    public UserInfoService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepository.findByEmail(username);

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        UserInfo user = userInfo.get();
        return new User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public String addUser(@RequestBody UserInfo userInfo) {
        if (userRepository.existsByEmail(userInfo.getEmail())) {
            throw new RuntimeException("User with email " + userInfo.getEmail() + " already exists");
        }

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setCreatedAt(LocalDateTime.now());


        userRepository.save(userInfo);
        return "User added successfully";
    }

    public Optional<UserInfo> getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}