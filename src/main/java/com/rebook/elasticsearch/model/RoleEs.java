package com.rebook.elasticsearch.model;


//@Document(indexName = "real_estate_schema", type = "role_es")
public class RoleEs {

//  @Id
  private String id;
  private String name;

  public RoleEs() { }

  public RoleEs(String name) {
    this.name = name;
  }

  public String getId() { return id; }

  public void setId(String id) { this.id = id; }

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

}
