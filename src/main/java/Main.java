import com.google.gson.Gson;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.lang.Boolean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.model.*;
import static app.model.Routes.*;

import static spark.Spark.*;

public class Main {

  public static void main(String[] args) {
    final String RESPONSE_SUCCESS  = "{\"status\":\"succeed\"}";
    final String RESPONSE_FAIL     = "{\"status\":\"fail\"}";    
    CredentialManager credentialManager = CredentialManager.getInstance();
    PersonDAO personDao = PersonDAO.getInstance();

    // JSON
    Gson gson = new Gson();

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    before((request,response)->{
      String method = request.requestMethod();
      // LOG REQUEST
      System.out.println("method: " + method + " path: " + request.pathInfo());
      if((method.equals("GET")) || (request.pathInfo().equals(SESSIONS_URL))){
        // do nothing
      }
      else{
        if(method.equals("POST") || 
            method.equals("PUT") || method.equals("DELETE")){
          Boolean authenticated = credentialManager.getAuthenticated();
          if(authenticated == false){
            halt(401, "You are not welcome here. Please visit /login page to continue.");
          }
        }
      }
    });

    // LOGIN/LOGOUT
    get(LOGIN_URL, (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("message", "Please login to continue.");
      return new ModelAndView(model, "login.ftl");
    }, new FreeMarkerEngine());

    get(SESSIONS_URL, (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      if(req.queryParams("_method").equals("DELETE")){
        credentialManager.signOutUser();
        model.put("message", "You have been signed out.");
        return new ModelAndView(model, "login.ftl");
      }
      else{
        return new ModelAndView(model, "index.ftl");
      }
    }, new FreeMarkerEngine());

    post(SESSIONS_URL, (req, res) -> {
      Map<String, Object> model = new HashMap<>();

      String username = req.queryParams("username");
      String password = req.queryParams("password"); 
      credentialManager.signInUser(username, password);
      if(credentialManager.getAuthenticated()){
        return new ModelAndView(model, "index.ftl");
      }
      else{
        model.put("message", "Please login to continue.");
        return new ModelAndView(model, "login.ftl");
      }
    }, new FreeMarkerEngine());


    // INDEX
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      if(credentialManager.getAuthenticated()){
        return new ModelAndView(model, "index.ftl");
      }
      else{
        model.put("message", "Please login to continue.");
        return new ModelAndView(model, "login.ftl");
      }
    }, new FreeMarkerEngine());

    // CRUD
    // GET /persons
    get(PERSONS_URL, (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      try {
        //return "{\"message\":5}";
        ArrayList<PersonDTO> people = personDao.getPeople();
        res.type("application/json");
        return people;
      } catch (Exception e) {
        res.status(500);
        return "{\"status\":\"fail\"}";
      }
    }, gson::toJson);

    // POST /persons
    post(PERSONS_URL, (req, res) -> {
        res.type("application/json");
      Map<String, Object> model = new HashMap<>();
      try {
        String name = req.queryParams("name"); 
        personDao.createPerson(name);
        res.status(200);
        return RESPONSE_SUCCESS;
      } catch (Exception e) {
        res.status(500);
        return RESPONSE_FAIL;
      }
    });

    // DELETE /person/:id
    delete(PERSONS_URL_ID, (req, res) -> {
      res.type("application/json");
      Map<String, Object> model = new HashMap<>();
      try {
        int id = Integer.parseInt(req.params(":id")); 
        personDao.deletePerson(id);
        res.status(200);
        return RESPONSE_SUCCESS;
      } catch (Exception e) {
        res.status(500);
        return RESPONSE_FAIL;
      }
    });

    // PUT /person/:id
    put(PERSONS_URL_ID, (req, res) -> {
      res.type("application/json");
      Map<String, Object> model = new HashMap<>();
      try {
        int id = Integer.parseInt(req.params(":id")); 
        String name = req.queryParams("name"); 
        personDao.updatePerson(id, name);
        res.status(200);
        return RESPONSE_SUCCESS;
      } catch (Exception e) {
        res.status(500);
        return RESPONSE_FAIL;
      }
    });

  }

}
