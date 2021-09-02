package com.example.buildingmanagement

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.provider.AlarmClock
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

val displayMetrics = DisplayMetrics()

//val height = displayMetrics.heightPixels / displayMetrics.density //手機真實高度
//val width = displayMetrics.widthPixels / displayMetrics.density //手機真實寬度

//val window: Window = dialog.getWindow()

class loginActivity : AppCompatActivity() {
    private val TAG = "loginActivity"

    var heightPixels = 0
    var widthPixels = 0

    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    val array = arrayListOf("社區1", "社區2", "社區3", "社區4", "社區5", "社區6", "社區7", "社區8", "社區9", "社區10",
        "社區11", "社區12", "社區13", "社區14", "社區15" )

    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportActionBar?.hide() //隱藏title
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        setContentView(R.layout.login_loading)
        val handler = Handler()
        handler.postDelayed({setContentView(R.layout.login_landing)}, 3000)

        getDeviceWH() // initialize get device width and height

//        val listViewClickListener = ListViewClickListener()
//        listViewClickListener.onItemClick()
//
//        listView.setOnItemClickListener { parent, view, position, id ->
//            val element = adapter.getItemAtPosition(position) // The item that was clicked
//            val intent = Intent(this, loginActivity::class.java)
//            Log.d(TAG, "list view Listener " + element)
//            Log.d(TAG, "list view Listener intert" + intent)
//            startActivity(intent)
//        }

    }

    fun loginbtn(view: View){
        setContentView(R.layout.activity_login)
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    fun openDialog(view: View) {
        alertDialog = AlertDialog.Builder(this)
        val rowList: View = layoutInflater.inflate(R.layout.my_dialog, container,false)
        listView = rowList.findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        listView.adapter = adapter

        // title
        var title = TextView(this)
        title.text = "選擇社區"
        title.setTextColor(R.color.forgotresidentcode)
        title.textSize = 20F

        alertDialog.setCustomTitle(title)
        alertDialog.setPositiveButton("確定") { dialog, which ->  }
        alertDialog.setNegativeButton("取消") { dialog: DialogInterface?, which: Int ->  }
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog)
        dialog.show()
        dialog.window?.setLayout((widthPixels*0.8).toInt(), (heightPixels*0.674).toInt())
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(resources.getString(R.color.Choosecommunity)))     //確定
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor(resources.getString(R.color.loading)))     //取消
    }

    fun closeDialog(view: View) {
        dialog.dismiss()
    }


    // 住戶代碼不存在
    @SuppressLint("ResourceType")
    fun userCodeErrorDialog() {
        alertDialog = AlertDialog.Builder(this, R.style.MyDialogStyle)
            .setMessage("請確認您輸入的 iPad 住戶代碼正確無誤，再試一次吧！")
            .setPositiveButton("好", null)   // listener argument can write some action

        // title
        var title = TextView(this)
        title.text = "住戶代碼不存在"
        title.setTextColor(R.color.forgotresidentcode)
        title.textSize = 16F

        alertDialog.setCustomTitle(title)


        dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog)
        dialog.show()
        dialog.window?.setLayout((widthPixels*0.778).toInt(), (heightPixels*0.281).toInt())
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(resources.getString(R.color.loading)))

    }

    //住戶代碼已被其他裝置綁定
    @SuppressLint("ResourceType")
    fun userCodeUsing(){
        alertDialog = AlertDialog.Builder(this)
            .setMessage("若您需要更換新的裝置，請洽詢社區管理中心協助解除綁定，並重新登入即可繼續此服務。")
            .setPositiveButton("好", null)   // listener argument can write some action

        // title
        var title = TextView(this)
        title.text = "住戶代碼已被其他裝置綁定"
        title.setTextColor(R.color.forgotresidentcode)
        title.textSize = 16F

        alertDialog.setCustomTitle(title)

        dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog)
        dialog.show()

        dialog.window?.setLayout((widthPixels*0.778).toInt(), (heightPixels*0.313).toInt())
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(resources.getString(R.color.loading)))

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
        if (landingedit.text.isNullOrEmpty()) {
            userCodeErrorDialog()
        }
        val button = findViewById<Button>(R.id.landingbutton)
//        val intent = Intent(this, MainActivity::class.java).apply {
//            putExtra(AlarmClock.EXTRA_MESSAGE, intent)
//        }
//        startActivity(intent)
    }

    // get device width and height
    fun getDeviceWH() {
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        widthPixels = displayMetrics.widthPixels
        heightPixels = displayMetrics.heightPixels

//        Log.d(TAG, "getDeviceWH: width " + displayMetrics.widthPixels)
//        Log.d(TAG, "getDeviceWH: height " + displayMetrics.heightPixels)
    }


    fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(listView!=null){
            listView!!.setBackgroundColor(listView!!.getResources().getColor(R.color.loading))
        }
        p1?.setBackgroundColor(p1.getResources().getColor(R.color.loading))
        listView= p1 as ListView
    }

}

private fun <T> ArrayAdapter<T>.getItemAtPosition(position: Int): Any {
    TODO("Not yet implemented")
}










