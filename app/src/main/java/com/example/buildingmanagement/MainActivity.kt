package com.example.buildingmanagement

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
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
        this.supportActionBar?.hide() //隱藏title
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) // 確認取消半透明設置。
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN// 全螢幕顯示，status bar 不隱藏，activity 上方 layout 會被 status bar 覆蓋。
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) // 配合其他 flag 使用，防止 system bar 改變後 layout 的變動。
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS) // 跟系統表示要渲染 system bar 背景。
            window.statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.decorView.systemUiVisibility = flags
            window.statusBarColor = Color.TRANSPARENT
        }


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
}