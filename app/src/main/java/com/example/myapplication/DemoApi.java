package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DemoApi {
    @GET("index")
    Call<Post> getPosts();
}
