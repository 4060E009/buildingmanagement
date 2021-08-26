package com.example.buildingmanagement

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.home.*


val displayMetrics = DisplayMetrics()

val heightPixels = displayMetrics.heightPixels //手機高度
val widthPixels = displayMetrics.widthPixels

val height = displayMetrics.heightPixels / displayMetrics.density //手機真實高度
val weight = displayMetrics.widthPixels / displayMetrics.density //手機真實寬度

//val window: Window = dialog.getWindow()

class loginActivity : AppCompatActivity() {
    private val TAG = "loginActivity"

    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    val array = arrayListOf("社區1", "社區2", "社區3", "社區4", "社區5", "社區6", "社區7", "社區8", "社區9", "社區10",
        "社區11", "社區12", "社區13", "社區14", "社區15" )

    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportActionBar?.hide() //隱藏title
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



//        val returnBtn = findViewById<View>(R.id.returnBtn) as ImageButton
//        returnBtn.setOnClickListener {
//            val intent1 = Intent(this, MainActivity::class.java).apply {
//                putExtra(AlarmClock.EXTRA_MESSAGE, intent)
//            }
//            startActivity(intent1)
//        }
    }


    @SuppressLint("ResourceAsColor")
    fun openDialog(view: View) {
        alertDialog = AlertDialog.Builder(this)
        val rowList: View = layoutInflater.inflate(R.layout.my_dialog, null)
        listView = rowList.findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        listView.adapter = adapter
        alertDialog.setPositiveButton("確定") { dialog, which -> }
        alertDialog.setNegativeButton("取消") { dialog: DialogInterface?, which: Int ->  }
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        dialog = alertDialog.create()
        dialog.setTitle(R.string.Choosecommunity1)
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.Choosecommunity)     //確定
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(14F)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.Choosecommunity)     //取消
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(14F)
//        dialog.getWindow()?.setLayout(heightPixels as Int, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    fun closeDialog(view: View) {
        dialog.dismiss()
    }

    fun forgot(view: View){
        setContentView(R.layout.forgotresidentcode)
    }

    fun privacypolicy(view: View){
        setContentView(R.layout.privacypolicy)
        val handler = Handler()
        handler.postDelayed({setContentView(R.layout.privacypolicy1)}, 1000)
    }

    fun landingbutton3(view: View) {
        setContentView(R.layout.home)
    }
}










