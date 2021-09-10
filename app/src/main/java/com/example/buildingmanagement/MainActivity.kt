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
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    companion object {
        val homeFragment = homeFragment()
        val scanFragment = scanFragment()
        val settingFragment = settingFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        this.supportActionBar?.hide() //隱藏title
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit()
        navigation.selectedItemId = R.id.navigation_home
        navigation.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.navigation_home -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container, homeFragment).commit()
                }
                R.id.navigation_scan -> {
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.container, scanFragment).commit()
                }
                R.id.navigation_setting -> {
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.container, settingFragment).commit()
                }
                else -> false
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private var listener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        //設定navigationBar裡的item們的點擊事件 被點擊後採動態載入fragment的方式
        override fun onNavigationItemSelected(item : MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.container, homeFragment).commit()
                }
                R.id.navigation_scan -> {
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.container, scanFragment).commit()
                }
                R.id.navigation_setting -> {
                    val t = supportFragmentManager.beginTransaction()
                    t.replace(R.id.container, settingFragment).commit()
                }
            }
            return true
        }
    }
}