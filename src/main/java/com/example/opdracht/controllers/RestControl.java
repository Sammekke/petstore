package com.example.opdracht.controllers;

import com.example.opdracht.exceptions.AnimalException;
import com.example.opdracht.models.Animal;
import com.example.opdracht.repositories.AnimalRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Optional;

import static java.lang.String.valueOf;

@RestController
@AllArgsConstructor
public class RestControl {
    private AnimalRepository animalRepository;

    @PostMapping(value = "/saveanimal", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> saveAnimal(@Valid @RequestBody Animal animal) {
        animalRepository.save(animal);
        return ResponseEntity.created(URI
                        .create(String.format("/animal/%s", animal.getId())))
                        .body("Added animal with id " + animal.getId());
    }

    @GetMapping(value = "/getAnimal")
    public ResponseEntity<String> getAnimal(@RequestParam String id) throws AnimalException {
        Long _id = 0L;
        try {
            _id = Long.parseLong(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The id you entered is not a number");
        }

        Optional<Animal> animalResponse = animalRepository.findById(_id);
        if (animalResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id " + _id + " is not found");
        }
        Animal foundAnimal = animalResponse.get();
        return ResponseEntity.status(HttpStatus.OK).body( valueOf(foundAnimal));
    }

    @PutMapping(value = "/updateAnimal")
    public ResponseEntity<String> updateAnimal(@RequestParam String id, @RequestBody Animal animal) {
        Long _id = 0L;
        try {
            _id = Long.parseLong(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The id you entered is not a number");
        }

        Optional<Animal> animalResponse = animalRepository.findById(_id);
        if (animalResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id " + _id + " is not found");
        }
        Animal foundAnimal = animalResponse.get();
        foundAnimal.setAge(animal.getAge());
        foundAnimal.setName(animal.getName());
        foundAnimal.setType(animal.getType());
        animalRepository.save(foundAnimal);

        return ResponseEntity.status(HttpStatus.OK).body("pet with id " + _id + " is updated");
    }

    @DeleteMapping(value = "/deleteAnimal")
    public ResponseEntity<String> updateAnimal(@RequestParam String id) {
        Long _id = 0L;
        try {
            _id = Long.parseLong(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The id you entered is not a number");
        }

        Optional<Animal> animalResponse = animalRepository.findById(_id);
        if (animalResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id " + _id + " is not found");
        }
        Animal foundAnimal = animalResponse.get();
        animalRepository.delete(foundAnimal);

        return ResponseEntity.status(HttpStatus.OK).body("pet with id " + _id + " is deleted");
    }
}