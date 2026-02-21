package com.spring.vmc.userpet.service;

import com.spring.vmc.userpet.model.dto.UserDto;
import com.spring.vmc.userpet.util.UserDtoConverter;
import com.spring.vmc.userpet.model.entity.Pet;
import com.spring.vmc.userpet.model.entity.User;
import com.spring.vmc.userpet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.getUserById(id);
        return UserDtoConverter.toDto(user);
    }

    public UserDto createUser(UserDto user) {
        User createdUser = userRepository.createUser(UserDtoConverter.toEntity(user));
        return UserDtoConverter.toDto(createdUser);
    }

    public UserDto updateUser(Long id, UserDto user) {
        User updatedUser = userRepository.updateUser(id, UserDtoConverter.toEntity(user));
        return UserDtoConverter.toDto(updatedUser);
    }

    public void delete(Long id) {
        List<Pet> pets = userRepository.getUserById(id).getPets();
        if (!pets.isEmpty()) {
            pets.forEach(pet -> pet.setUserId(null));
        }
        userRepository.delete(id);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        return users.stream()
                .map(UserDtoConverter::toDto)
                .toList();
    }
}
