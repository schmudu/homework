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

  private static final String COMMAND_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS persons (id serial primary key, name varchar(255))";
  private static final String COMMAND_CREATE_PERSON = "INSERT INTO persons (name) VALUES ('%s')";
  private static final String COMMAND_DELETE_PERSON = "DELETE FROM persons WHERE id=%d";
  private static final String COMMAND_UPDATE_PERSON = "UPDATE persons SET name='%s' WHERE id=%d";
  private static final String COMMAND_SELECT_PERSONS = "SELECT id, name FROM persons";

  protected PersonDAO(){
    config = new  HikariConfig();
    config.setJdbcUrl(System.getenv("JDBC_DATABASE_URL"));
  }

  public static PersonDAO getInstance(){
    if(instance == null){
      instance = new PersonDAO();
    }
    return instance;
  }

  public void deletePerson(int id) throws Exception{
    HikariDataSource dataSource = null;
    Connection connection = null;
    try{
       dataSource = (config.getJdbcUrl() != null) ?
        new HikariDataSource(config) : new HikariDataSource();
      connection = dataSource.getConnection();
      Statement stmt = connection.createStatement();
      stmt.executeUpdate(COMMAND_CREATE_TABLE);
      String deleteSql = String.format(COMMAND_DELETE_PERSON, id);
      stmt.executeUpdate(deleteSql);
    }
    catch(Exception e){
      System.out.println("Exception deleting person: " + e);
    }
    finally{
      try{ 
        if(dataSource != null){
          dataSource.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while deleting person: " + e); 
      }
      try{ 
        if(connection != null){
          connection.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while generating SQL statement: " + e); 
      }
    }
  }

  public void updatePerson(int id, String name) throws Exception{
    HikariDataSource dataSource = null;
    Connection connection = null;
    try{
      dataSource = (config.getJdbcUrl() != null) ?
        new HikariDataSource(config) : new HikariDataSource();
      connection = dataSource.getConnection();
      Statement stmt = connection.createStatement();
      stmt.executeUpdate(COMMAND_CREATE_TABLE);
      String deleteSql = String.format(COMMAND_UPDATE_PERSON, name, id);
      stmt.executeUpdate(deleteSql);
    }
    catch(Exception e){
      System.out.println("Exception deleting person: " + e);
    }
    finally{
      try{ 
        if(dataSource != null){
          dataSource.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while deleting person: " + e); 
      }
      try{ 
        if(connection != null){
          connection.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while generating SQL statement: " + e); 
      }
    }
  }

  public void createPerson(String name) throws Exception{
    HikariDataSource dataSource = null;
    Connection connection = null;
    try{
      dataSource = (config.getJdbcUrl() != null) ?
        new HikariDataSource(config) : new HikariDataSource();
      connection = dataSource.getConnection();
      Statement stmt = connection.createStatement();
      stmt.executeUpdate(COMMAND_CREATE_TABLE);

      String insertSql = String.format(COMMAND_CREATE_PERSON, name);
      stmt.executeUpdate(insertSql);
    }
    catch(Exception e){
      System.out.println("Exception creating person: " + e);
    }
    finally{
      try{ 
        if(dataSource != null){
          dataSource.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while creating person: " + e); 
      }
      try{ 
        if(connection != null){
          connection.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while generating SQL statement: " + e); 
      }
    }
  }

  public ArrayList<PersonDTO> getPeople() throws Exception{
    ResultSet rs = null;
    HikariDataSource dataSource = null;
    ArrayList<PersonDTO> output = new ArrayList<PersonDTO>();
    Connection connection = null;
    try{
      dataSource = (config.getJdbcUrl() != null) ?
        new HikariDataSource(config) : new HikariDataSource();
      connection = dataSource.getConnection();
      Statement stmt = connection.createStatement();
      stmt.executeUpdate(COMMAND_CREATE_TABLE);

      rs = stmt.executeQuery(COMMAND_SELECT_PERSONS);

      while (rs.next()) {
        output.add(new PersonDTO(new Integer(rs.getInt("id")), new String(rs.getString("name"))));
      }

    }
    catch(Exception e){
      System.out.println("Exception creating person: " + e);
    }
    finally{
      try{ 
        if(dataSource != null){
          dataSource.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while getting everyone: " + e); 
      }
      try{ 
        if(rs != null){
          rs.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while getting everyone: " + e); 
      }
      try{ 
        if(connection != null){
          connection.close(); 
        }
      } catch(Exception e){ 
        System.out.println("Exception closing connection while generating SQL statement: " + e); 
      }
    }
    return output;
  }
}
