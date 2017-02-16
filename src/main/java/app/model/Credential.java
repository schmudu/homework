package app.model;

public class Credential{
  private String username;
  private String password;

  public Credential(String newUsername, String newPassword){
    username = newUsername;
    password = newPassword;
  }

  public String getUsername(){
    return username;
  }

  public void setUsername(String newUsername){
    username = newUsername;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(String newPassword){
    password = newPassword;
  }
}