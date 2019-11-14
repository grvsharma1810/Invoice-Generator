package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    String BASE_URL = "http://10.0.2.2:8000/";
    EditText orgName;
    EditText add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orgName = (EditText)findViewById(R.id.orgName);
        add = (EditText)findViewById(R.id.add);
    }

    public void postData(View view){
        String org = orgName.getText().toString();
        String address = add.getText().toString();
        //Toast.makeText(getApplicationContext(),org+" "+address,Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShopApi shopApi = retrofit.create(ShopApi.class);
        Shop shop = new Shop(org,address);
        Call<Shop> shopCall = shopApi.create(shop);
        shopCall.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                }
                Shop shopResponse = response.body();
                String content = shopResponse.getOrgName() + shopResponse.getAddress();
                Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Open new activity

    public void openActivityItems(View view){
        Intent intent = new Intent(this,items.class);
        startActivity(intent);
    }

    public void render(View view){
        Intent intent = new Intent(this,webViewActivity.class);
        startActivity(intent);
    }

    public void openActivity2(View view){
        Intent intent = new Intent(this,Activity2.class);
        startActivity(intent);
    }
}
