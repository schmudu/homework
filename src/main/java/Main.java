import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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
    CredentialManager credentialManager = CredentialManager.getInstance();
    PersonDAO personDao = PersonDAO.getInstance();

    // JSON
    Gson gson = new Gson();

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    before((request,response)->{
      String method = request.requestMethod();
      // login
      System.out.println("path: " + request.pathInfo());
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
      credentialManager.signInUser(req);
      if(credentialManager.getAuthenticated()){
        return new ModelAndView(model, "index.ftl");
      }
      else{
        model.put("message", "Please login to continue.");
        return new ModelAndView(model, "login.ftl");
      }
    }, new FreeMarkerEngine());


    // CRUD
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

    HikariConfig config = new  HikariConfig();
    config.setJdbcUrl(System.getenv("JDBC_DATABASE_URL"));
    final HikariDataSource dataSource = (config.getJdbcUrl() != null) ?
      new HikariDataSource(config) : new HikariDataSource();

    get("/db", (req, res) -> {
      Map<String, Object> attributes = new HashMap<>();
      try(Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add( "Read from DB: " + rs.getTimestamp("tick"));
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      }
    }, new FreeMarkerEngine());

/*
    get("/persons", (req, res) -> {
      //res.type("application/json");
      //return "{\"message\":5}";
      Map<String, Object> attributes = new HashMap<>();
      try(Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS persons (name string)");
        ResultSet rs = stmt.executeQuery("SELECT name FROM persons");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add(rs.getString("name"));
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "index.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      }
    });
*/
    get("/persons", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      try {
        //return "{\"message\":5}";
        ArrayList<PersonDTO> people = personDao.getPeople();
        res.type("application/json");
        return people;
      } catch (Exception e) {
        return null;
      }
    }, gson::toJson);

  }

}
