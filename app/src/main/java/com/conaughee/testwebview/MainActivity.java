package com.conaughee.testwebview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.conaughee.testwebview.core.DeviceChecker;
import com.conaughee.testwebview.core.FirebaseWrapper;
import com.conaughee.testwebview.tetris.TetrisActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseWrapper firebaseWrapper;
    private DeviceChecker deviceChecker;

    private boolean canGoBack;

    private void ShowContent() {
        startActivity(new Intent(this, TetrisActivity.class));
    }

    private void UpdateContent() {
        String url = firebaseWrapper.getUrl();
        if (deviceChecker.suitableDevice() && !url.isEmpty()) ShowWeb(url);
        else ShowContent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canGoBack = true;
        deviceChecker = new DeviceChecker(this);
        firebaseWrapper = new FirebaseWrapper(this);
        UpdateContent();
    }

    @Override
    protected void onResume(){
        super.onResume();
        UpdateContent();
    }

    @Override
    public void onBackPressed() {
        if (canGoBack) super.onBackPressed();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void ShowWeb(String url) {
        setContentView(R.layout.activity_main);
        WebView webview = findViewById(R.id.test_web);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);
        canGoBack = false;
    }
}