package com.spring.vmc.userpet.service;

import com.spring.vmc.userpet.model.User;
import com.spring.vmc.userpet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    public User updateUser(Long id, User user) {
        return userRepository.updateUser(id, user);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
