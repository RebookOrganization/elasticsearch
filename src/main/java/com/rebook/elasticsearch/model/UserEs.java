package com.rebook.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.validation.constraints.Email;

//@Document(indexName = "real_estate_schema", type = "user_es")
public class UserEs {

//  @Id
  private String id;
  private String name;
  @Email
  private String email;
  private boolean emailVerified;
  @JsonIgnore
  private String password;
  private String providerId;
  private String imageUrl;
  private String phoneNumber;
  private String birthDate;
  private String gender;
  private Date lastLogin;
  private String ipLogin;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getLastLogin() { return lastLogin; }

  public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }

  public String getIpLogin() { return ipLogin; }

  public void setIpLogin(String ipLogin) { this.ipLogin = ipLogin; }

  public boolean isEmailVerified() { return emailVerified; }

  public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
}
