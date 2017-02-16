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
  private static HikariConfig config = null;
  private static HikariDataSource dataSource = null;

  private static final String COMMAND_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS persons (id serial primary key, name varchar(255))";
  private static final String COMMAND_CREATE_PERSON = "INSERT INTO persons (name) VALUES ('%s')";
  private static final String COMMAND_DELETE_PERSON = "DELETE FROM persons WHERE id=%d";
  private static final String COMMAND_UPDATE_PERSON = "UPDATE persons SET name='%s' WHERE id=%d";
  private static final String COMMAND_SELECT_PERSONS = "SELECT id, name FROM persons";

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

  public void deletePerson(int id) throws Exception{
    Statement stmt = generateSqlStatement();
    String deleteSql = String.format(COMMAND_DELETE_PERSON, id);
    stmt.executeUpdate(deleteSql);
  }

  public void updatePerson(int id, String name) throws Exception{
    Statement stmt = generateSqlStatement();
    String deleteSql = String.format(COMMAND_UPDATE_PERSON, name, id);
    stmt.executeUpdate(deleteSql);
  }

  public void createPerson(String name) throws Exception{
    Statement stmt = generateSqlStatement();
    String insertSql = String.format(COMMAND_CREATE_PERSON, name);
    stmt.executeUpdate(insertSql);
  }

  public ArrayList<PersonDTO> getPeople() throws Exception{
    Statement stmt = generateSqlStatement();
    ResultSet rs = stmt.executeQuery(COMMAND_SELECT_PERSONS);
    ArrayList<PersonDTO> output = new ArrayList<PersonDTO>();
    while (rs.next()) {
      output.add(new PersonDTO(rs.getInt("id"), rs.getString("name")));
    }

    return output;
  }

  private static Statement generateSqlStatement() throws Exception{
    Connection connection = dataSource.getConnection();
    Statement stmt = connection.createStatement();
    stmt.executeUpdate(COMMAND_CREATE_TABLE);
    return stmt;
  }
}