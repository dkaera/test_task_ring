package com.testproject.kaera.ringtestapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.testproject.kaera.ringtestapp.R
import com.testproject.kaera.ringtestapp.controllers.TopListController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ActionBarProvider {

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        processRouter(savedInstanceState)
    }

    private fun processRouter(savedInstanceState: Bundle?) {
        router = Conductor.attachRouter(this, controller_container, savedInstanceState)
        if (router!!.hasRootController()) return
        router!!.setRoot(RouterTransaction.with(TopListController(null)))
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) super.onBackPressed()
    }
}