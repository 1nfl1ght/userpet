package com.spring.vmc.userpet.controller;

import com.spring.vmc.userpet.model.User;
import com.spring.vmc.userpet.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        log.info("getUserById method started");
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("getAllUsers method started");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(users);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("createUser method started");
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User user) {
        log.info("updateUser method started");
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
        log.info("deleteUser method started");
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
