package com.testproject.kaera.ringtestapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.controllers.TopListController;

import butterknife.BindView;

import static butterknife.ButterKnife.*;

public class MainActivity extends AppCompatActivity implements ActionBarProvider {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.controller_container) ViewGroup container;

    private Router router;

    @Override protected void onCreate(Bundle savedInstanceState) {
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
        router.setRoot(RouterTransaction.with(new TopListController()));
    }

    @Override public void onBackPressed() {
        if (!router.handleBack()) super.onBackPressed();
    }
}