package com.testproject.kaera.ringtestapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.testproject.kaera.ringtestapp.R

class MainActivity : AppCompatActivity(), ActionBarProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }




}