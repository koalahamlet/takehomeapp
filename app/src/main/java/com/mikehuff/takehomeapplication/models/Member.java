package com.mikehuff.takehomeapplication.models;

public class Member {

  String id;

  String name;

  String color;

  String realName;

  String tz;

  Profile profile;

  String presence;

  boolean deleted;

  public boolean isDeleted() {
    return deleted;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getColor() {
    return color;
  }

  public String getRealName() {
    return realName;
  }

  public String getTz() {
    return tz;
  }

  public Profile getProfile() {
    return profile;
  }

  public String getPresence() {
    return presence;
  }
}
