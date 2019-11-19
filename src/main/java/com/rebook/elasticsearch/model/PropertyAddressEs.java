package com.rebook.elasticsearch.model;


//@Document(indexName = "rebook_property_address_es", type = "property_address_es")
public class PropertyAddressEs {

//    @Id
    private String id;
    private String street;
    private String district;
    private String province;
    private String summary;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    public String getProvince() { return province; }

    public void setProvince(String province) { this.province = province; }

    public String getSummary() { return summary; }

    public void setSummary(String summary) { this.summary = summary; }
}
