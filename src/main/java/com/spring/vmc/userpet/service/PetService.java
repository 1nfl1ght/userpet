package com.spring.vmc.userpet.service;

import com.spring.vmc.userpet.model.Pet;
import com.spring.vmc.userpet.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    public Pet getPetById(Long id) {
        return petRepository.getPetById(id);
    }

    public Pet createPet(Pet pet) {
        return petRepository.createPet(pet);
    }

    public Pet updatePet(Pet pet, Long id) {
        return petRepository.updatePet(pet, id);
    }

    public void deletePetById(Long id) {
        petRepository.deletePetById(id);
    }

    public List<Pet> getAllPets() {
        return petRepository.getAllPets();
    }
}
