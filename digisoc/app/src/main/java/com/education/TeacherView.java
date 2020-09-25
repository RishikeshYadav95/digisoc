package com.education;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TeacherView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);
        WebView browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl("http://digisoc.in/digisoc/");
        browser.setWebViewClient(new WebViewClient());
    }
}
