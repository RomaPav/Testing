package org.example.testing.model;/*
  @author   user
  @project   lab4
  @class  EntityEditRequest
  @version  1.0.0 
  @since 27.03.2024 - 22.48
*/

public record EntityEditRequest(String id, String name, String code, String description) {
    public Boolean validName(){
        return (name != null && !name.isEmpty() && name.length() >3 && !name.contains("test"));
    }
    public  Boolean validDescription(){ return !description.contains("test"); }
}