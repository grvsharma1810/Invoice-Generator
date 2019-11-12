package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity2 extends Activity {

    ImageView imageView;
    String BASE_URL = "http://10.0.2.2:8000/";
    Bitmap decodedByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        imageView = (ImageView) findViewById((R.id.img));
    }

    public void share(View view){
        Uri uri = saveImageExternal(decodedByte);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent , "Share"));
    }

    private Uri saveImageExternal(Bitmap image) {
        //TODO - Should be processed in another thread
        Uri uri = null;
        try {
            Bitmap newBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(image, 0, 0, null);
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.jpg");
            FileOutputStream stream = new FileOutputStream(file);
            newBitmap.setHasAlpha(true);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            Log.d("Error", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
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
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
