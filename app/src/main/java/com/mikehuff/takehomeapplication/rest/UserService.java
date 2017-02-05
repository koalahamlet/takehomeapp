package com.mikehuff.takehomeapplication.rest;

import com.mikehuff.takehomeapplication.models.TeamResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

  @GET("api/users.list")
  Call<TeamResponse> getUserList(@Query("token") String authToken,
                                 @Query("presence") String presence,
                                 @Query("pretty") String pretty);

}
