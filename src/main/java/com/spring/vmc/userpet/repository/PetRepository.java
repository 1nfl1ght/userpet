package com.spring.vmc.userpet.repository;

import com.spring.vmc.userpet.exception.ResourceNotFoundException;
import com.spring.vmc.userpet.model.Pet;
import com.spring.vmc.userpet.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PetRepository {

    private Map<Long, Pet> pets;
    private Long idCounter;
    private final UserRepository userRepository;

    public PetRepository(UserRepository userRepository) {
        this.pets = new HashMap<>();
        this.idCounter = 0L;
        this.userRepository = userRepository;
    }

    public Pet getPetById(Long id) {
        return Optional.ofNullable(pets.get(id)).orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));
    }

    public Pet createPet(Pet pet) {
        Long newId = ++idCounter;
        User user = Optional.ofNullable(userRepository.getUserById(pet.getUserId())).orElseThrow(() -> new ResourceNotFoundException("User with id " + pet.getUserId() + " not found"));
        Pet createdPet = new Pet(
                newId,
                pet.getName(),
                pet.getUserId()
        );
        user.getPets().add(createdPet);
        pets.put(newId, createdPet);
        return createdPet;
    }

    public Pet updatePet(Pet pet, Long id) {
        Pet oldPet = Optional.ofNullable(pets.get(id)).orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));
        if (!Objects.equals(oldPet.getUserId(), pet.getUserId())) {
            User oldUser = Optional.ofNullable(userRepository.getUserById(oldPet.getUserId())).orElseThrow(() -> new ResourceNotFoundException("User with id + " + oldPet.getUserId() + " not found"));
            User newUser = Optional.ofNullable(userRepository.getUserById(pet.getUserId())).orElseThrow(() -> new ResourceNotFoundException("User with id + " + pet.getUserId() + " not found"));
            oldUser.getPets().removeIf(currentPet -> currentPet.getId().equals(oldPet.getId()));
            newUser.getPets().add(pet);
            pets.put(id, pet);
        }
        Pet newPet = new Pet(
                id,
                pet.getName(),
                pet.getUserId()
        );
        pets.put(id, newPet);
        return newPet;
    }

    public void deletePetById(Long id) {
        if (pets.get(id) == null) {
            throw new ResourceNotFoundException("Pet with id " + id + " not found");
        }
        User user = userRepository.getUserById(pets.get(id).getUserId());
        user.getPets().removeIf(pet -> Objects.equals(pet.getId(), id));
        pets.remove(id);
    }

    public List<Pet> getAllPets() {
        return pets.values().stream().toList();
    }
}
