package facades;

import DTO.BreedDTO;
import DTO.DogDTO;
import entities.Breed;
import entities.Dog;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DogFacade {

    private static EntityManagerFactory emf;
    private static DogFacade instance;

    private DogFacade() {
    }

    public static DogFacade getDogFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();
        }
        return instance;
    }

    public List<BreedDTO> getBreeds(){
        EntityManager em = emf.createEntityManager();
        List<BreedDTO> breedDTOList = new ArrayList<>();
        try {
            TypedQuery<Breed> query = em.createQuery("SELECT b FROM Breed b", Breed.class);
            for(Breed breed : query.getResultList()){
                breedDTOList.add( new BreedDTO(breed) );
            }
        } finally {
            em.close();
        }

        return breedDTOList;
    }

    public void addBreedsToDB(List<Breed> breeds){
        EntityManager em = emf.createEntityManager();

        try {
            for (Breed b : breeds){
                em.persist(b);
            }
        } finally {
            em.close();
        }
    }

    public Breed getBreedByname(String breedName){
        EntityManager em = emf.createEntityManager();
        Breed breed = null;

        try {
            TypedQuery query = em.createQuery("SELECT b FROM Breed b WHERE b.name = :name",Breed.class);
            query.setParameter("name",breedName);
            breed = (Breed) query.getSingleResult();
        } finally {
            em.close();
        }

        return breed;
    }

    public List<DogDTO> getUserDogs(String username){
        EntityManager em = emf.createEntityManager();
        List<DogDTO> dogDTOList = new ArrayList<>();

        try{
            User user = em.find(User.class, username);

            for(Dog dog : user.getDogs()){
                dogDTOList.add( new DogDTO(dog) );
            }
        } finally {
            em.close();
        }
        return dogDTOList;
    }

    public void adduserDog(String username, Dog dog){
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, username);
            user.addDog(dog);

            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
