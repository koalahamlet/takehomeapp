package com.mikehuff.takehomeapplication.models;

import java.util.List;

public class TeamResponse {

  List<Member> members;
  long cacheTs;

  public List<Member> getMembers() {
    return members;
  }

  public long getCacheTs() {
    return cacheTs;
  }

}
