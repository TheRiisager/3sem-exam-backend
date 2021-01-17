package utils;


import entities.Breed;
import entities.Dog;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "123");
    User admin = new User("admin", "123");
    User both = new User("user_admin", "123");

    Breed beagle = new Breed(0, "Beagle","Beagles are dogs");
    Breed shiba = new Breed(1, "Shiba","shibas are dogs");

    Dog dog1 = new Dog("Fido", "01/01/2019", "Fido er en god hund", beagle);
    Dog dog2 = new Dog("Doge", "02/02/2020", "wow, such dog, many personality",shiba);

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    user.addDog(dog1);
    admin.addDog(dog2);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(beagle);
    em.persist(shiba);
    em.persist(dog1);
    em.persist(dog2);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
