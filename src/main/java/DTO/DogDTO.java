package DTO;

import entities.Dog;

public class DogDTO {
    private String name;
    private String dateOfBirth;
    private String info;
    private String breed;

    public DogDTO(Dog dog){
        this.name = dog.getName();
        this.dateOfBirth = dog.getDateOfBirth();
        this.info = dog.getInfo();
        this.breed = dog.getBreed().getName();
    }
}
