package com.testproject.kaera.ringtestapp.controllers;

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
import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;
import com.testproject.kaera.ringtestapp.service.command.AuthenticateCommand;
import com.testproject.kaera.ringtestapp.service.util.RxPreferences;
import com.testproject.kaera.ringtestapp.util.Constants;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import butterknife.BindView;
import io.techery.janet.ActionPipe;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.testproject.kaera.ringtestapp.util.Constants.CLIENT_ID;
import static com.testproject.kaera.ringtestapp.util.Constants.REDIRECT_URI;

public class AuthDialogController extends BaseController {

    private static final String BASE_AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s&response_type=code&state=%s&redirect_uri=%s&duration=permanent&scope=read";
    private static final String STATE = "TEST";
    private static final String AUTH_URL = String.format(BASE_AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI);

    private String authCode;
    private boolean restored;

    @BindView(R.id.web_view) WebView webView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Inject RxPreferences preferences;
    @Inject ActionPipe<AuthenticateCommand> authenticateCommand;

    public AuthDialogController(Bundle args) {
        super(args);
    }

    public AuthDialogController() {
        super();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        RingApplication.getComponent().inject(this);
        return super.onCreateView(inflater, container);
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
                    preferences.getString(Constants.KEY_AUTH_CODE).set(authCode);

                    Log.d("TAG", "CODE : " + authCode);
                    Toast.makeText(getApplicationContext(), "Authorization Code is: " + authCode, Toast.LENGTH_SHORT).show();
                    authenticateCommand.send(new AuthenticateCommand());
                } else if (url.contains("error=access_denied")) {
                    Log.d("TAG", "ACCESS_DENIED_HERE");
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

    @Override protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        authenticateCommand.observeSuccess()
                .map(command -> command.getResult())
                .subscribe(s -> Log.e("TAG", "onSubscribe value " + s));
    }
}
