package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "breeds")
public class Breed {

    @Id
    @Basic(optional = false)
    @NotNull
    private int id;

    private String name;
    private String info;

    @OneToMany(mappedBy = "breed")
    private List<Dog> dogs;

    @OneToMany(mappedBy = "breed")
    private List<Search> searches;

    public Breed() {}

    public Breed(@NotNull int id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.dogs = new ArrayList<>();
        this.searches = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void addDog(Dog dog){
        this.dogs.add(dog);
    }

    public void removeDog(Dog dog){
        if(this.dogs.contains(dog)){
            this.dogs.remove(dog);
        }
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}


