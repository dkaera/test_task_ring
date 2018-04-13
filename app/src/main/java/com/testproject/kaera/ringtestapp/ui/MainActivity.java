package com.testproject.kaera.ringtestapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.controllers.TopListController;
import com.testproject.kaera.ringtestapp.service.util.RxPreferences;

import javax.inject.Inject;

import butterknife.BindView;

import static butterknife.ButterKnife.*;

public class MainActivity extends AppCompatActivity implements ActionBarProvider {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.controller_container) ViewGroup container;

    @Inject RxPreferences preferences;

    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind(this);
        setSupportActionBar(toolbar);
        RingApplication.getComponent().inject(this);
        processRouter(savedInstanceState);
    }

    private void processRouter(Bundle savedInstanceState) {
        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (router.hasRootController()) return;
//        boolean isAuthCodeExist = preferences.getString(Constants.KEY_AUTH_CODE).isSet();
//        router.setRoot(RouterTransaction.with(isAuthCodeExist ? new HomeController() : new TopListController()));
        router.setRoot(RouterTransaction.with(new TopListController()));
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) super.onBackPressed();
    }
}