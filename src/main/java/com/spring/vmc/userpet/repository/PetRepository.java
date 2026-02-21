package com.spring.vmc.userpet.repository;

import com.spring.vmc.userpet.exception.ResourceNotFoundException;
import com.spring.vmc.userpet.model.entity.Pet;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PetRepository {

    private Map<Long, Pet> pets;
    private Long idCounter;

    public PetRepository() {
        this.pets = new HashMap<>();
        this.idCounter = 0L;
    }

    public Pet getPetById(Long id) {
        return Optional.ofNullable(pets.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Pet with id " + id + " not found"));
    }

    public Pet createPet(Pet pet) {
        Long newId = ++idCounter;
        Pet createdPet = new Pet(
                newId,
                pet.getName(),
                pet.getUserId()
        );
        pets.put(newId, createdPet);
        return createdPet;
    }

    public Pet updatePet(Long id, Pet pet) {
        Pet newPet = new Pet(
                id,
                pet.getName(),
                pet.getUserId()
        );
        pets.put(id, newPet);
        return newPet;
    }

    public void deletePetById(Long id) {
        pets.remove(id);
    }

    public List<Pet> getAllPets() {
        return pets.values().stream().toList();
    }
}
