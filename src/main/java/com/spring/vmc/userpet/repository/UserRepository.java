package com.spring.vmc.userpet.repository;

import com.spring.vmc.userpet.exception.ResourceNotFoundException;
import com.spring.vmc.userpet.model.Pet;
import com.spring.vmc.userpet.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private Map<Long, User> users;
    private Long idCounter;

    public UserRepository() {
        this.users = new HashMap<>();
        this.idCounter = 0L;
    }

    public User getUserById(Long id) {
        return Optional.ofNullable(users.get(id)).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    public User createUser(User user) {
        Long newId = ++idCounter;

        User createdUser = new User(
                newId,
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
        users.put(newId, createdUser);
        return createdUser;
    }

    public User updateUser(Long id, User user) {
        User userToUpdate = Optional.ofNullable(users.get(id)).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setAge(user.getAge());
        return users.put(id, userToUpdate);
    }

    public void delete(Long id) {
        List<Pet> pets = Optional.ofNullable(
                        users.get(id)
                )
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"))
                .getPets();
        if (!pets.isEmpty()) {
            pets.forEach(pet -> pet.setUserId(null));
        }
        users.remove(id);
    }

    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }
}
