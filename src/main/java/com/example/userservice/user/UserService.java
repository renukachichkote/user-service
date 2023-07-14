package com.example.userservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPrivileges(getPrivileges(user));
        return userRepository.save(user);
    }

    public List<String> getPrivileges(User user) {
        List<String> privileges = new ArrayList<>();
        if (user.getRoles().equals("ADMIN")) {
             privileges = List.of("CREATE_USER", "READ_USER", "READ_ALL_USER", "UPDATE_USER", "DELETE_USER",
                            "SAVE_BOOK", "READ_BOOK", "READ_ALL_BOOK", "UPDATE_BOOK", "DELETE_BOOK",
                            "BORROW_BOOK", "READ_ALL_TRANSACTIONS", "READ_TRANSACTION", "RETURN_BOOK");
        } else if (user.getRoles().equals("USER")) {
            privileges = List.of("USER_READ", "READ_BOOK", "READ_ALL_BOOK",
                            "BORROW_BOOK", "READ_ALL_TRANSACTIONS",
                            "READ_TRANSACTION", "RETURN_BOOK");
        }
        return privileges;
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (null != user.getUsername()) {
                existingUser.setUsername(user.getUsername());
            }
            if(null != user.getPassword()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(existingUser);
            return existingUser;
        } else {
            return null;
        }
    }
}
