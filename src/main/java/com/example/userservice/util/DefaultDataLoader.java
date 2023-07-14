package com.example.userservice.util;

import com.example.userservice.user.User;
import com.example.userservice.user.UserRepository;
import com.example.userservice.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class DefaultDataLoader {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public void loadDefaultAdminUser(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("default-admin.json");
            User adminUser = objectMapper.readValue(inputStream, User.class);

            // Check if the default admin user already exists in the database
            if (userRepository.findByUsername(adminUser.getUsername()).isEmpty()) {
                adminUser = userService.saveUser(adminUser);
                log.debug("Default admin user created successfully: [{}]", adminUser);
            } else {
                log.debug("Default admin user already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
