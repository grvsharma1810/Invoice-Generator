package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView canvas = (WebView) findViewById(R.id.canvas);
        canvas.setWebViewClient(new WebViewClient());
        canvas.loadUrl("http://10.0.2.2:8000/save");
        WebSettings webSettings = canvas.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
