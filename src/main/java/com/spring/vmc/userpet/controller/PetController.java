package com.spring.vmc.userpet.controller;

import com.spring.vmc.userpet.model.Pet;
import com.spring.vmc.userpet.service.PetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PetController {

    private static final Logger log = LoggerFactory.getLogger(PetController.class);

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable("id") Long id) {
        Pet pet = petService.getPetById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(pet);
    }

    @PostMapping("/pets")
    public ResponseEntity<Pet> createPet(@RequestBody @Valid Pet pet) {
        Pet createdPet = petService.createPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPet);
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<Pet> updatePet(@RequestBody @Valid Pet pet, @PathVariable("io") Long id) {
        Pet updatedPet = petService.updatePet(pet, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedPet);
    }

    @DeleteMapping("/pets")
    public ResponseEntity<Pet> deletePetById(@PathVariable("id") Long id) {
        petService.deletePetById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
