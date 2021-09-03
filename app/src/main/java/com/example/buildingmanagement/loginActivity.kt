package com.example.buildingmanagement

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.AlarmClock
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.buildingmanagement.HttpApi.HttpApi
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import kotlin.properties.Delegates

val displayMetrics = DisplayMetrics()

//val height = displayMetrics.heightPixels / displayMetrics.density //手機真實高度
//val width = displayMetrics.widthPixels / displayMetrics.density //手機真實寬度

//val window: Window = dialog.getWindow()

class loginActivity : AppCompatActivity() {
    private val TAG = "loginActivity"

    var heightPixels = 0
    var widthPixels = 0

    val httpApi = HttpApi()

    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    lateinit var current: View
    var isfirstview:Boolean = true

//    val array = arrayListOf("社區1", "社區2", "社區3", "社區4", "社區5", "社區6", "社區7", "社區8", "社區9", "社區10",
//        "社區11", "社區12", "社區13", "社區14", "社區15" )

    var array: ArrayList<String> = arrayListOf()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportActionBar?.hide() //隱藏title
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_loading)
        val handler = Handler()
        handler.postDelayed({setContentView(R.layout.login_landing)}, 3000)


        getDeviceWH() // initialize get device width and height

        httpApi.GetAllProjectName(object: HttpApi.OnRequestListener {
            override fun onSuccess(result: Any) {
                Log.d(TAG, "onSuccess: " + result)
                val list = JSONArray(result as String)
                for (i in 0 until list.length()) {
                    Log.d(TAG, "for " + list[i])
                    array.add(list[i] as String)
                }
            }

            override fun onError() {
//                TODO("Not yet implemented")
            }
        })

    }

    fun loginbtn(view: View){
        setContentView(R.layout.activity_login)

        landingedit.addTextChangedListener(object : TextWatcher {
            @SuppressLint("ResourceAsColor")
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                landingbutton3.setBackgroundResource(R.drawable.enable_btn)
                landingbutton3.setTextColor(resources.getColor(R.color.white))
                landingedit.setBackgroundResource(R.drawable.edit_border)
                Log.d(TAG, "beforeTextChanged: ")
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (landingedit.text.isEmpty()){
                    landingbutton3.setBackgroundResource(R.drawable.shape_circle)
                    landingbutton3.setTextColor(resources.getColor(R.color.Choosecommunity))
                    Log.d(TAG, "onTextChanged: ")
                }
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged: ")
            }

        })
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    fun openDialog(view: View) {
        alertDialog = AlertDialog.Builder(this)
        val rowList: View = layoutInflater.inflate(R.layout.my_dialog, container,false)
        listView = rowList.findViewById(R.id.listView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            spinner.text = array[position]
            spinner.setTextColor(R.color.forgotresidentcode)
//            view.setBackgroundResource(R.drawable.list_view)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(resources.getString(R.color.loading)))

            if (current != view && isfirstview){
                current.setBackgroundResource(R.layout.my_dialog)
                current = view
            }else{
                view.setBackgroundResource(R.drawable.list_view)
                current = view
//                current.setBackgroundColor(R.drawable.list_view)
            }
        }

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
            .setPositiveButton("好", null)   // listener argument can write some action

        // title
        var title = TextView(this)
        title.text = "住戶代碼不存在"
        title.setTextColor(R.color.forgotresidentcode)
        title.textSize = 16F

        alertDialog.setCustomTitle(title)

        //message
        var message = SpannableString("請確認您輸入的 iPad 住戶代碼正確無誤，再試一次吧！")
            message.setSpan(ForegroundColorSpan(R.color.Choosecommunity),0,message.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        alertDialog.setMessage(message)

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
            .setPositiveButton("好", null)   // listener argument can write some action

        // title
        var title = TextView(this)
        title.text = "住戶代碼已被其他裝置綁定"
        title.setTextColor(R.color.forgotresidentcode)
        title.textSize = 16F

        alertDialog.setCustomTitle(title)

        //message
        var message = SpannableString("若您需要更換新的裝置，請洽詢社區管理中心協助解除綁定，並重新登入即可繼續此服務。")
        message.setSpan(ForegroundColorSpan(R.color.Choosecommunity),0,message.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        alertDialog.setMessage(message)

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

    // login and start MainActivity
    @SuppressLint("ResourceAsColor")
    fun landingClick(view: View) {
        val button = findViewById<Button>(R.id.landingbutton)

        if (landingedit.text.isNullOrEmpty()) {
            landingedit.setBackgroundResource(R.drawable.error_border)
            userCodeErrorDialog()
        }
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, intent)
        }
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

    fun backLoginLanding(view: View) {
        setContentView(R.layout.login_landing)
    }

    fun close_view(view: View){
        setContentView(R.layout.activity_login)
    }

}











