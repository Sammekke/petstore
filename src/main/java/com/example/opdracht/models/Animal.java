package com.example.opdracht.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message= "name is required")
    @NotNull(message= "name is required")
    @JsonProperty("type")
    private String type;
    @NotBlank(message= "name is required")
    @NotNull(message= "name is required")
    @JsonProperty("name")
    private String name;
    @NotNull(message= "age is required")
    @JsonProperty("age")
    private Integer age;

    @Override
    public String toString() {
        return "{id:"+id+", name: "+ name +", type:"+ type + ", age:"+ age +"}";
    }
}