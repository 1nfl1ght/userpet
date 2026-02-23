package com.spring.vmc.userpet.controller;

import com.spring.vmc.userpet.model.dto.PetDto;
import com.spring.vmc.userpet.model.entity.Pet;
import com.spring.vmc.userpet.service.PetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private static final Logger log = LoggerFactory.getLogger(PetController.class);

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public PetDto getPetById(@PathVariable("id") Long id) {
        return petService.getPetById(id);
    }

    @GetMapping()
    public List<PetDto> getAllPets() {
        return petService.getAllPets();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PetDto createPet(@RequestBody @Valid PetDto pet) {
        return petService.createPet(pet);
    }

    @PutMapping("/{id}")
    public PetDto updatePet(@PathVariable("id") Long id, @RequestBody @Valid PetDto pet) {
        return petService.updatePet(id, pet);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePetById(@PathVariable("id") Long id) {
        petService.deletePetById(id);
    }
}
