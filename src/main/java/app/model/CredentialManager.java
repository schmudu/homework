package app.model;

import java.util.ArrayList;
import spark.Request;

public class CredentialManager{
  private static CredentialManager instance;
  private ArrayList<Credential> CREDENTIALS = createCredentials();
  private Boolean authenticated = false;

  protected CredentialManager(){
    // singleton
  }

  public static CredentialManager getInstance(){
    if(instance == null){
      instance = new CredentialManager();
    }
    return instance;
  }

  public Boolean getAuthenticated(){
    return authenticated;
  }

  public void signOutUser(){
    authenticated = false;
  }

  public void signInUser(String username, String password){
    // do nothing if no credentials
    if ((username == null) || (password == null) ||
      (username == "") || (password == "")){
      return;
    }

    Credential currentCredential;
    for(Credential credential : CREDENTIALS){
      if((credential.getUsername().equals(username)) &&
        (credential.getPassword().equals(password))){
        authenticated = true;
      }
    }
  }
  
  private ArrayList<Credential> createCredentials(){
    ArrayList<Credential> credentials = new ArrayList();
    credentials.add(new Credential("JEFF", "SUPERPASS"));
    credentials.add(new Credential("JEFF2", "SUPERPASS"));
    return credentials;
  }
}