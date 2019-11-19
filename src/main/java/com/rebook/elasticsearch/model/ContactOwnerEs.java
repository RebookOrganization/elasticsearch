package com.rebook.elasticsearch.model;

//@Document(indexName = "rebook_contact_owner_es", type = "contact_owner_es")
public class ContactOwnerEs {

//    @Id
    private String id;
    private String contactName;
    private String address;
    private String phoneNumber;
    private String email;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getContactName() { return contactName; }

    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

}
