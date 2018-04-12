package com.testproject.kaera.ringtestapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.controllers.HomeController;
import com.testproject.kaera.ringtestapp.controllers.LoginController;
import com.testproject.kaera.ringtestapp.service.command.AuthenticateCommand;

import javax.inject.Inject;

import butterknife.BindView;
import io.techery.janet.ActionPipe;
import rx.functions.Action1;

import static butterknife.ButterKnife.*;

public class MainActivity extends AppCompatActivity implements ActionBarProvider {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.controller_container)
    ViewGroup container;

    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind(this);
        setSupportActionBar(toolbar);
        RingApplication.getComponent().inject(this);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) router.setRoot(RouterTransaction.with(new LoginController()));
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) super.onBackPressed();
    }
}