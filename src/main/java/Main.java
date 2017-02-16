import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> {
      RelativisticModel.select();

      String energy = System.getenv().get("ENERGY");

      Amount<Mass> m = Amount.valueOf(energy).to(KILOGRAM);
      return "E=mc^2: " + energy + " = " + m.toString();
    });

    get("/", (request, response) -> {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("message", "Hello World!");

        return new ModelAndView(attributes, "index.ftl");
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
  }

}
