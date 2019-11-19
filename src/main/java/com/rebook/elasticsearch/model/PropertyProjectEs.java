package com.rebook.elasticsearch.model;

//@Document(indexName = "rebook_property_project_es", type = "property_project_es")
public class PropertyProjectEs {

//    @Id
    private String id;
    private String projectName;
    private String projectOwner;
    private String projectSize;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getProjectName() { return projectName; }

    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectOwner() { return projectOwner; }

    public void setProjectOwner(String projectOwner) { this.projectOwner = projectOwner; }

    public String getProjectSize() { return projectSize; }

    public void setProjectSize(String projectSize) { this.projectSize = projectSize; }

}
