package rest;

import DTO.BreedDTO;
import DTO.DogDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Breed;
import entities.Dog;
import facades.DogFacade;
import utils.EMF_Creator;
import utils.JWTdecoder;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("dogs")
public class DogResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DogFacade DOG_FACADE = DogFacade.getDogFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("breeds")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBreeds(){
        List<BreedDTO> breedDTOList = DOG_FACADE.getBreeds();
        return Response.ok( GSON.toJson(breedDTOList) ).build();
    }

    @Path("getdogs")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user","admin"})
    public Response getUserDogs(@HeaderParam("x-access-token") String token){
        JWTdecoder decoder = new JWTdecoder(token);
        List<DogDTO> dogDTOList = DOG_FACADE.getUserDogs( decoder.getUserName() );
        return Response.ok( GSON.toJson(dogDTOList) ).build();
    }

    @Path("add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user","admin"})
    public String addUserDog(@HeaderParam("x-access-token") String token, String body){
        JWTdecoder decoder = new JWTdecoder(token);
        
        JsonObject jsonbody = JsonParser.parseString(body).getAsJsonObject();
        String name = jsonbody.get("name").getAsString();
        String info = jsonbody.get("info").getAsString();
        String dateOfBirth = jsonbody.get("dateOfBirth").getAsString();
        Breed breed = DOG_FACADE.getBreedByname( jsonbody.get("breed").getAsString() );
        Dog dog = new Dog(name,dateOfBirth,info,breed);

        try {
            DOG_FACADE.adduserDog(decoder.getUserName(), dog);
            return "{" +
                    "msg: success" +
                    "}";
        } catch (Exception e){
            return "{" +
                    "msg: fail" +
                    "}";
        }
    }

}
