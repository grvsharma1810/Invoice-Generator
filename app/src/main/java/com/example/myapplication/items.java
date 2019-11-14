package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class items extends AppCompatActivity {

    String BASE_URL = "http://10.0.2.2:8000/";
    EditText name ;
    EditText quantity ;
    EditText rate ;
    EditText amount ;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        name = (EditText) findViewById(R.id.name);
        quantity = (EditText) findViewById(R.id.quantity);
        rate = (EditText) findViewById(R.id.rate);
        amount = (EditText) findViewById(R.id.amount);
        itemList = new ArrayList<Item>();
    }

    public void more(View view){
        Item newItem = new Item(name.getText().toString(),
                quantity.getText().toString(),
                amount.getText().toString(),
                rate.getText().toString());
        itemList.add(newItem);
        name.setText("");
        quantity.setText("");
        amount.setText("");
        rate.setText("");
    }

    public void addItems(View view){
        Retrofit itemRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ItemApi itemApi = itemRetrofit.create(ItemApi.class);
        Call<List<Item>> itemCall = itemApi.createItems(itemList);
        itemCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                }
                List<Item> responseItem = response.body();
                Toast.makeText(getApplicationContext(),"Item Added",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
