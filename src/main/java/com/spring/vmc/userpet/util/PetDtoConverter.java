package com.spring.vmc.userpet.util;

import com.spring.vmc.userpet.model.dto.PetDto;
import com.spring.vmc.userpet.model.entity.Pet;

public class PetDtoConverter {

    public static PetDto toDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getUserId()
        );
    }

    public static Pet toEntity(PetDto petDto) {
        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setUserId(petDto.getUserId());
        return pet;
    }
}
