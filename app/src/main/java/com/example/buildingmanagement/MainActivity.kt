package com.example.buildingmanagement

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide() //隱藏title
        setContentView(R.layout.login_loading)
        val handler = Handler()
        handler.postDelayed({setContentView(R.layout.login_landing)}, 3000)

    }

    fun loginbtn(view: View){
        val button = findViewById<Button>(R.id.landingbutton)
        val intent = Intent(this, loginActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, intent)
        }
        startActivity(intent)
    }

    private var listener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        //設定navigationBar裡的item們的點擊事件 被點擊後採動態載入fragment的方式
        override fun onNavigationItemSelected(item : MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
//                    transaction.replace(R.id.container_activity_main, homeFragment).commit()
                }
                R.id.navigation_scan -> {
                    val t = supportFragmentManager.beginTransaction()
//                    t.replace(R.id.container_activity_main, reservationFragment).commit()
                }
                R.id.navigation_setting -> {
                    val t = supportFragmentManager.beginTransaction()
//                    t.replace(R.id.container_activity_main, memberFragemnt).commit()
                }
            }
            return true
        }
    }
}