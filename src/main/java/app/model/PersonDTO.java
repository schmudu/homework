package app.model;

public class PersonDTO{
  private String name;
  private int id;

  public PersonDTO(int newId, String newName){
    name = newName;
    id = newId;
  }

  public String getName(){
    return name;
  }

  public void setName(String newName){
    name = newName;
  }

  public int getId(){
    return id;
  }

  public void setId(int newId){
    id = newId;
  }
}