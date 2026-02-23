package com.spring.vmc.userpet.util;

import com.spring.vmc.userpet.model.dto.UserDto;
import com.spring.vmc.userpet.model.entity.User;

public class UserDtoConverter {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAge(user.getAge());
        userDto.setPets(user.getPets());
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        user.setPets(userDto.getPets());
        return user;
    }
}
