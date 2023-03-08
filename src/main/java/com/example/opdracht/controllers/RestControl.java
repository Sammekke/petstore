package com.example.opdracht.controllers;

import com.example.opdracht.exceptions.AnimalException;
import com.example.opdracht.models.Animal;
import com.example.opdracht.repositories.AnimalRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static java.lang.String.valueOf;

@RestController
@AllArgsConstructor
public class RestControl {
    private AnimalRepository animalRepository;

    @PostMapping(value = "/saveanimal", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> saveAnimal(@Validated @RequestBody Animal animal) {
        String missingAttribute = isAnimalObjectComplete(animal);
        if (missingAttribute != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The body does not contain a " + missingAttribute);
        };
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
        return ResponseEntity.status(HttpStatus.OK).body( valueOf(foundAnimal));
    }

    private String isAnimalObjectComplete(Animal animal) {
        String answer = null;
        if (animal.getName()==null) {
            answer = "name";
        } else if (animal.getType()==null) {
            answer = "type";
        } else if (animal.getAge()==null) {
            answer = "age";
        }
        return answer;
    }
}