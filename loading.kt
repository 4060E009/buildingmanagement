MainActivity.kt
**
package com.example.buildingmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getSupportActionBar()?.hide()          //隱藏title
        setContentView(R.layout.activity_main)
    }
}
================================================================================
 
