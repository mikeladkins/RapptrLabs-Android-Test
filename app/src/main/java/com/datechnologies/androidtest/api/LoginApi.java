package com.datechnologies.androidtest.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi
{
    @FormUrlEncoded
    @POST("/Tests/scripts/login.php")
    Call<String> login(@Field("email") String username, @Field("password") String password);
}
