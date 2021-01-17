package rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("dev")
public class DevResource {

    @Path("populateDatabase")
    @GET
    @RolesAllowed("admin")
    public String populateDatabase(){
        return "database populated";
    }


}
