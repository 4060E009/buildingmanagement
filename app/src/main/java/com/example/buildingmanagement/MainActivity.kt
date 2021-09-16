package com.example.buildingmanagement

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.homeinfo.*


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
        setContentView(R.layout.homeinfo)

        val androidBug5497Workaround = AndroidBug5497Workaround()
        androidBug5497Workaround.assistActivity(this)

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
        homeedit.addTextChangedListener(object : TextWatcher {
            @SuppressLint("ResourceAsColor")
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                homeedit.setTextColor(resources.getColor(R.color.forgotresidentcode))
                Log.d(TAG, "beforeTextChanged: ")
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d(TAG, "onTextChanged: ")
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged: ${homeedit.text.toString()}")
                if (homeedit.text.isNullOrEmpty()) {
                    homeinfobtn.isEnabled = false
                    homeinfobtn.setBackgroundResource(R.drawable.home_null)
                    homeinfobtn.setTextColor(resources.getColor(R.color.Choosecommunity))
                } else {
                    homeinfobtn.isEnabled = true
                    homeinfobtn.setBackgroundResource(R.drawable.home_check)
                    homeinfobtn.setTextColor(resources.getColor(R.color.white))
                }
            }
        })

    }

    fun navigation(view: View){
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
    //點擊空白處關閉鍵盤
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (this.currentFocus != null) {
                if (this.currentFocus!!.windowToken != null) {
                    imm.hideSoftInputFromWindow(
                        this.currentFocus!!.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            }
        }
        return super.onTouchEvent(event)
    }
}