package com.example.ideathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);
        Intent intent = getIntent();
        String url = getString(R.string.url);
        webView.setWebViewClient(new WebViewClient());

        if (intent.hasExtra(url)) {
            webView.loadUrl(intent.getStringExtra(url));
        }
    }
}
