package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {


    WebView webView;
    private String webUrl = "file:///C:/Users/brain/OneDrive/%E6%A1%8C%E9%9D%A2/yo%E4%B8%BB%E5%BB%9A.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.myWebView);
        webView.loadUrl(webUrl);
    }
}