package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "dogs")
public class Dog {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private String dateOfBirth;
    private String info;

    @ManyToOne
    @JoinColumn(name = "BREED_ID")
    private Breed breed;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private User owner;

    public Dog(){}

    public Dog(String name, String dateOfBirth, String info, Breed breed) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {

        this.breed.removeDog(this);

        this.breed = breed;
        this.breed.addDog(this);

    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
