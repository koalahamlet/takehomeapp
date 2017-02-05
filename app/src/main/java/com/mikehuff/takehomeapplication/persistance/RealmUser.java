package com.mikehuff.takehomeapplication.persistance;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RealmUser extends RealmObject {

  @Required
  @PrimaryKey
  private String userId;

  private String userName;

  private String realName;

  private String jobTitle;

  private String userAvatarUrl;

  private String presence;

  private String color;

  private String email;

  private String phone;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getUserAvatarUrl() {
    return userAvatarUrl;
  }

  public void setUserAvatarUrl(String userAvatarUrl) {
    this.userAvatarUrl = userAvatarUrl;
  }

  public String getPresence() {
    return presence;
  }

  public void setPresence(String presence) {
    this.presence = presence;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
