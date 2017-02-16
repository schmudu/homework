package app.model;

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

import java.util.ArrayList;

public class PersonDAO{
  private static PersonDAO instance = null;
  private HikariConfig config = null;
  private HikariDataSource dataSource = null;

  private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS persons (id serial primary key, name varchar(255))";

  protected PersonDAO(){
    config = new  HikariConfig();
    config.setJdbcUrl(System.getenv("JDBC_DATABASE_URL"));
    dataSource = (config.getJdbcUrl() != null) ?
      new HikariDataSource(config) : new HikariDataSource();
  }

  public static PersonDAO getInstance(){
    if(instance == null){
      instance = new PersonDAO();
    }
    return instance;
  }

  public void createPerson(String name) throws Exception{
    Connection connection = dataSource.getConnection();
    Statement stmt = connection.createStatement();
    stmt.executeUpdate(CREATE_TABLE_SQL);

    // insert
    System.out.println("going to add name: " + name);
    String insertSql = "INSERT INTO persons (name) VALUES ('%s')";
    insertSql = String.format(insertSql, name);
    stmt.executeUpdate(insertSql);
  }

  public ArrayList<PersonDTO> getPeople() throws Exception{
    Connection connection = dataSource.getConnection();
    Statement stmt = connection.createStatement();
    stmt.executeUpdate(CREATE_TABLE_SQL);

    ResultSet rs = stmt.executeQuery("SELECT id, name FROM persons");
    ArrayList<PersonDTO> output = new ArrayList<PersonDTO>();
    while (rs.next()) {
      output.add(new PersonDTO(rs.getInt("id"), rs.getString("name")));
    }

    return output;
  }
}