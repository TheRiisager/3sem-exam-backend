package rest;

import DTO.tempBreedDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import entities.Breed;
import facades.DogFacade;
import utils.EMF_Creator;
import utils.HttpHelper;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("dev")
public class DevResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DogFacade DOG_FACADE = DogFacade.getDogFacade(EMF);

    @Path("populateDatabase")
    @GET
    @RolesAllowed("admin")
    public String populateDatabase(){
        try {
            HttpHelper httpHelper = new HttpHelper();
            String jsonString = httpHelper.sendRequest("https://dog-info.cooljavascript.dk/api/breed/", "GET", new HashMap<String, String>(), "");
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            Type listType = new TypeToken<ArrayList<tempBreedDTO>>() {}.getType();
            List<tempBreedDTO> breedsTemp = new Gson().fromJson(jsonObject.get("dogs").getAsJsonArray(),listType);
            List<Breed> breeds = new ArrayList<>();

            for(int i = 0; i < breedsTemp.size(); i++){
                breeds.add( new Breed(i, breedsTemp.get(i).getBreed(),"Not implemented") );
            }

            DOG_FACADE.addBreedsToDB(breeds);

            return "database populated";
        } catch (IOException e){
            return "error: " + e.getLocalizedMessage();
        }
    }


}
