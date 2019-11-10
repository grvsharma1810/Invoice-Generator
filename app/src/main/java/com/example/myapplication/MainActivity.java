package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    ImageView imageView;
    String BASE_URL = "http://10.0.2.2:8000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById((R.id.img));
    }

    public void fun(View view){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DemoApi demoApi = retrofit.create(DemoApi.class);
        Call<Post> call = demoApi.getPosts();

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                }
                Post post = response.body();
                String msg = "name :" + post.getName() + "\n"
                        + "sentFrom : " + post.getSentFrom() + "\n"
                        + "time : " + post.getTime() + "\n"
                        + "msg : " + post.getMsg();
                //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                String base64String = post.getMsg();
                String base64Image = base64String.split(",")[1];

                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                imageView.setImageBitmap(decodedByte);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
