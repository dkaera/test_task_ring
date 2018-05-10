package com.testproject.kaera.ringtestapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.testproject.kaera.ringtestapp.R
import com.testproject.kaera.ringtestapp.controllers.TopListController


class MainActivity : AppCompatActivity(), ActionBarProvider {

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val container = findViewById<ViewGroup>(R.id.container)
        setSupportActionBar(toolbar)

        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (router!!.hasRootController()) return
        router!!.setRoot(RouterTransaction.with(TopListController()))
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) super.onBackPressed()
    }



}