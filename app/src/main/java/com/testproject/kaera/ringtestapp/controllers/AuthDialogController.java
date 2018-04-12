package com.testproject.kaera.ringtestapp.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;

import butterknife.BindView;

import static android.view.View.*;

public class AuthDialogController extends BaseController {

    private static String CLIENT_ID = "6EDgCzywHhEoiQ";
    private static String REDIRECT_URI = "http://www.example.com/redirect";
    private static final String BASE_AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s&response_type=code&state=%s&redirect_uri=%s&duration=permanent&scope=read";
    private static final String STATE = "TEST";
    private static final String AUTH_URL = String.format(BASE_AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI);

    private String authCode;
    private Intent resultIntent = new Intent();
    private boolean restored;

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public AuthDialogController(Bundle args) {
        super(args);
    }

    public AuthDialogController() {
        super();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_dialog_auth, container, false);
    }

    @Override
    protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
        if (savedViewState != null) {
            webView.restoreState(savedViewState);
            restored = true;
            showProgress(false);
        }
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        if (restored) return;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.loadUrl(String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI));
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgress(true);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showProgress(false);
                if (url.contains("?code=") || url.contains("&code=")) {
                    Uri uri = Uri.parse(url);
                    authCode = uri.getQueryParameter("code");
                    Log.e("TAG", "CODE : " + authCode);
                    resultIntent.putExtra("code", authCode);
                    Toast.makeText(getApplicationContext(), "Authorization Code is: " + authCode, Toast.LENGTH_SHORT).show();
                    getRouter().popController(AuthDialogController.this);
                } else if (url.contains("error=access_denied")) {
                    Log.e("TAG", "ACCESS_DENIED_HERE");
                    resultIntent.putExtra("code", authCode);
                    Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                    getRouter().popController(AuthDialogController.this);
                }
            }
        });
    }

    private void showProgress(boolean visible) {
        progressBar.setVisibility(visible ? VISIBLE : GONE);
        webView.setVisibility(visible ? GONE : VISIBLE);
    }
}
