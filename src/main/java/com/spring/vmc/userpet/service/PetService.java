package com.spring.vmc.userpet.service;

import com.spring.vmc.userpet.exception.ResourceNotFoundException;
import com.spring.vmc.userpet.model.dto.PetDto;
import com.spring.vmc.userpet.model.entity.Pet;
import com.spring.vmc.userpet.model.entity.User;
import com.spring.vmc.userpet.repository.PetRepository;
import com.spring.vmc.userpet.repository.UserRepository;
import com.spring.vmc.userpet.util.PetDtoConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public PetDto getPetById(Long id) {
        Pet pet = petRepository.getPetById(id);
        return PetDtoConverter.toDto(pet);
    }

    public PetDto createPet(PetDto petDto) {
        Pet pet = PetDtoConverter.toEntity(petDto);
        User user = userRepository.getUserById(pet.getUserId());
        Pet created = petRepository.createPet(pet);
        user.getPets().add(created);
        return PetDtoConverter.toDto(created);
    }

    public PetDto updatePet(Long id, PetDto petDto) {
        Pet newPetData = PetDtoConverter.toEntity(petDto);
        Pet oldPet = petRepository.getPetById(id);
        if (oldPet.getUserId() != null) {
            User oldUser = userRepository.getUserById(oldPet.getUserId());
            oldUser.getPets().removeIf(p -> p.getId().equals(id));
        }
        Pet updatedPet = petRepository.updatePet(id, newPetData);
        if (updatedPet.getUserId() != null) {
            User newUser = userRepository.getUserById(updatedPet.getUserId());
            newUser.getPets().add(updatedPet);
        }

        return PetDtoConverter.toDto(updatedPet);
    }

    public void deletePetById(Long id) {
        Pet pet = petRepository.getPetById(id);
        User user = userRepository.getUserById(pet.getUserId());
        user.getPets().removeIf(p -> Objects.equals(p.getId(), id));
        petRepository.deletePetById(id);
    }

    public List<PetDto> getAllPets() {
        List<Pet> pets = petRepository.getAllPets();
        return pets.stream()
                .map(PetDtoConverter::toDto)
                .toList();
    }
}
