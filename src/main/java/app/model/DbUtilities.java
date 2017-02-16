package app.model;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;

public class DbUtilities{
  protected DbUtilities(){
    // prevent instantiation
  }

  public static void closeDataSource(HikariDataSource dataSource){
    try{ 
      if(dataSource != null){
        dataSource.close(); 
      }
    } catch(Exception e){ 
      System.out.println("Exception closing HikariDataSource: " + e); 
    }
  }

  public static void closeSqlConnection(Connection connection){
    try{ 
      if(connection != null){
        connection.close(); 
      }
    } catch(Exception e){ 
      System.out.println("Exception closing SQL connection: " + e); 
    }
  }

  public static void closeResultSet(ResultSet rs){
    try{ 
      if(rs != null){
        rs.close(); 
      }
    } catch(Exception e){ 
      System.out.println("Exception closing ResultSet: " + e); 
    }
  }
}
