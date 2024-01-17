package at.technikum.apps.mtcg.controller;

import at.technikum.apps.mtcg.entity.Card;
import at.technikum.apps.mtcg.entity.Package;
import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.server.http.HttpStatus;
import at.technikum.server.http.Request;
import at.technikum.server.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

public class PackageController extends AbstractController{

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @Override
    public boolean supports(String route) {
        return route.startsWith("/packages");
    }

    @Override
    public Response handle(Request request) {
        if (request.getMethod().equals("POST")){
            return createPackage(request);
        }
        return notAllowed();
    }

    public Response createPackage(Request request){
        if (!isLoggedInAsAdmin(request)){
            return unauthorized();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        try {
            Set<Card> cards = objectMapper.readValue(request.getBody(),  new TypeReference<>(){});
            Package packageToBeCreated = new Package(cards);
            packageToBeCreated = packageService.save(packageToBeCreated);
            String pkgJson = objectMapper.writeValueAsString(packageToBeCreated);
            return json(HttpStatus.CREATED, pkgJson);
        } catch (JsonProcessingException e) {
            System.out.println(e);
            return badRequest();
        }
    }
}
