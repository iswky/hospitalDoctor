package com.example.patact.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patact.R;

public class IbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ib);

        // Найти WebView по идентификатору
        WebView webView = findViewById(R.id.webView);

        // Получить строку "ibText" из Intent
        String ibText = getIntent().getStringExtra("ibText");

        // Включить поддержку JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Загрузить HTML-контент в WebView
        webView.loadDataWithBaseURL(null, ibText, "text/html", "UTF-8", null);

        // Установить WebViewClient, чтобы открытие ссылок оставалось внутри WebView
        webView.setWebViewClient(new WebViewClient());
    }
}
