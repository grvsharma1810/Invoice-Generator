package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ShopApi {
    @POST("temp")
    Call<Shop> create(@Body Shop shop);
}
