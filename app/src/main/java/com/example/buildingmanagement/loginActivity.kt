package com.example.buildingmanagement

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
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
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import com.example.buildingmanagement.HttpApi.HttpApi
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.homeinfo.*
import kotlinx.android.synthetic.main.listview_item.*
import kotlinx.android.synthetic.main.login_landing.*
import kotlinx.android.synthetic.main.privacypolicy1.*
import org.json.JSONArray


val displayMetrics = DisplayMetrics()

class loginActivity : AppCompatActivity() {
    private val TAG = "loginActivity"

    var heightPixels = 0
    var widthPixels = 0

    val httpApi = HttpApi()

    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog

    var selectname: String = ""

//    val array = arrayListOf("社區1", "社區2", "社區3", "社區4", "社區5", "社區6", "社區7", "社區8", "社區9", "社區10",
//        "社區11", "社區12", "社區13", "社區14", "社區15" )

    var array: ArrayList<String> = arrayListOf()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide() //隱藏title
        setContentView(R.layout.login_loading)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) // 確認取消半透明設置。
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN// 全螢幕顯示，status bar 不隱藏，activity 上方 layout 會被 status bar 覆蓋。
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) // 配合其他 flag 使用，防止 system bar 改變後 layout 的變動。
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS) // 跟系統表示要渲染 system bar 背景。
            window.statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
            window.decorView.systemUiVisibility = flags
            window.statusBarColor = Color.TRANSPARENT
        }
        val handler = Handler()
        handler.postDelayed({
            initLandingPage()
        }, 3000)

        Log.d(TAG, "onCreate: ${savedInstanceState}")
        Log.d(TAG, "onCreate: hasExtra reload ${intent.hasExtra("reload")}")

        getDeviceWH() // initialize get device width and height

        httpApi.GetAllProjectName {
            onSuccess {
                Log.d(TAG, "GetAllProjectName onSuccess: ${it}")
                val list = JSONArray(it as String)
                for (i in 0 until list.length()) {
                    Log.d(TAG, "item -> ${list[i]}")
                    array.add(list[i] as String)
                }
            }
        }

//        httpApi.BindUserData("1", "2", "3") {
//             onSuccess {
//                 it as BindUserDat
//                 it.address
//             }
//        }


    }

    fun initLandingPage() {
        setContentView(R.layout.login_landing)
        imageview.layoutParams.height = (heightPixels * 0.669).toInt()
        textview.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")
        textview1.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Bold.otf")
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f)
        textview1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24f)
        (textview.layoutParams as LinearLayout.LayoutParams).apply {
            topMargin = (heightPixels * 0.041).toInt()
            bottomMargin = (heightPixels * 0.016).toInt()

        }
        (textview1.layoutParams as LinearLayout.LayoutParams).apply {
            topMargin = (heightPixels * 0.041).toInt()
            bottomMargin = (heightPixels * 0.016).toInt()
        }
        (textview2.layoutParams as RelativeLayout.LayoutParams).apply {
            bottomMargin = (heightPixels * 0.063).toInt()
        }
        textview2.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Regular.otf")
        textview2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)
        (landingbutton.layoutParams as RelativeLayout.LayoutParams).apply {
            height = (heightPixels * 0.069).toInt()
            width = (widthPixels * 0.8).toInt()
            bottomMargin = (heightPixels * 0.056).toInt()
            leftMargin = (widthPixels * 0.1).toInt()
        }
        landingbutton.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")
        landingbutton.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)

        Log.d( TAG, "initLandingPage: tv_top=${(heightPixels * 0.041).toInt()}" )
        Log.d(TAG, "initLandingPage: tv_bottom=${(heightPixels*0.016).toInt()}")
        Log.d(TAG, "initLandingPage: tv2_bottom=${(heightPixels*0.063).toInt()}")

    }

    fun loginbtn(view: View){
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) // 確認取消半透明設置。
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 全螢幕顯示，status bar 不隱藏，activity 上方 layout 會被 status bar 覆蓋。
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) // 配合其他 flag 使用，防止 system bar 改變後 layout 的變動。
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS) // 跟系統表示要渲染 system bar 背景。
            window.statusBarColor = Color.TRANSPARENT
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
            window.decorView.systemUiVisibility = flags
            window.statusBarColor = Color.TRANSPARENT
        }

        logintext.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")
        textview3.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Regular.otf")
        landingbutton3.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")
        textview4.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Regular.otf")
        textview5.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Regular.otf")

        landingbutton3.isEnabled = false
        landingedit.addTextChangedListener(object : TextWatcher {
                @SuppressLint("ResourceAsColor")
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    landingedit.setBackgroundResource(R.drawable.edit_border)
                    Log.d(TAG, "beforeTextChanged: ")
                }

                @SuppressLint("ResourceAsColor")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (landingedit.text.isEmpty() || spinner.text.isNullOrEmpty()){

                        Log.d(TAG, "onTextChanged: ")
                    }
                }

                @SuppressLint("ResourceAsColor")
                override fun afterTextChanged(s: Editable?) {
                    Log.d(TAG, "afterTextChanged: ")
                    if (landingedit.text.isNullOrEmpty() || selectname.isNullOrEmpty()) {
                        landingbutton3.isEnabled = false
                        landingbutton3.setBackgroundResource(R.drawable.shape_circle)
                        landingbutton3.setTextColor(resources.getColor(R.color.Choosecommunity))
                    } else {
                        landingbutton3.isEnabled = true
                        landingbutton3.setBackgroundResource(R.drawable.enable_btn)
                        landingbutton3.setTextColor(resources.getColor(R.color.white))
                    }
                }
            })
            // activity_login.xml spinner and EditText setting width and height
            (spinner.layoutParams as RelativeLayout.LayoutParams).apply {
                height = (heightPixels * 0.069).toInt()
                width = (widthPixels * 0.919).toInt()
            }
            (landingedit.layoutParams as RelativeLayout.LayoutParams).apply {
                height = (heightPixels * 0.069).toInt()
                width = (widthPixels * 0.919).toInt()
            }
            (landingbutton3.layoutParams as LinearLayout.LayoutParams).apply {
                height = (heightPixels * 0.069).toInt()
                width = (widthPixels * 0.911).toInt()

            }
        }

    @SuppressLint("ResourceAsColor", "ResourceType")
    fun openDialog(view: View) {
        alertDialog = AlertDialog.Builder(this)
        val rowList: View = layoutInflater.inflate(R.layout.my_dialog, container,false)
        listView = rowList.findViewById(R.id.listView)

        adapter = ArrayAdapter(this, R.layout.listview_item, array)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            listView.setItemChecked(position,true);

            spinner.text = array[position]
            selectname = array[position]
            if (landingedit.text.isNullOrEmpty() || selectname.isNullOrEmpty()) {
                landingbutton3.isEnabled = false
                landingbutton3.setBackgroundResource(R.drawable.shape_circle)
                landingbutton3.setTextColor(resources.getColor(R.color.Choosecommunity))
            } else {
                landingbutton3.isEnabled = true
                landingbutton3.setBackgroundResource(R.drawable.enable_btn)
                landingbutton3.setTextColor(resources.getColor(R.color.white))
            }
            spinner.setTextColor(resources.getColor(R.color.forgotresidentcode))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(resources.getString(R.color.loading)))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
        }

        // title
        var title = TextView(this)
        title.text = "選擇社區"
        title.setTextColor(resources.getColor(R.color.forgotresidentcode))
        title.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")
        title.textSize = 20F

        alertDialog.setCustomTitle(title)
        alertDialog.setPositiveButton("確定") { dialog, which ->  }
        alertDialog.setNegativeButton("取消") { dialog: DialogInterface?, which: Int -> closeDialog() }
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog)
        dialog.show()

        // dialog height
        if (array.size > 8) {
            dialog.window?.setLayout((widthPixels*0.8).toInt(), (heightPixels*0.674).toInt())
        } else {
            dialog.window?.setLayout((widthPixels*0.8).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(resources.getString(R.color.confirm)))     //確定
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor(resources.getString(R.color.loading)))     //取消
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")

    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    fun closeDialog() {
        spinner.text = resources.getString(R.string.Choosecommunity)
        spinner.setTextColor(Color.parseColor(resources.getString(R.color.Choosecommunity)))
        selectname = ""
        dialog.dismiss()
        // disable button
        landingbutton3.isEnabled = false
        landingbutton3.setBackgroundResource(R.drawable.shape_circle)
        landingbutton3.setTextColor(resources.getColor(R.color.Choosecommunity))
    }

    // 住戶代碼不存在
    @SuppressLint("ResourceType")
    fun userCodeErrorDialog() {
        alertDialog = AlertDialog.Builder(this, R.style.MyDialogStyle)
            .setPositiveButton("好", null)   // listener argument can write some action

        // title
        var title = TextView(this)
        title.text = "住戶代碼不存在"
        title.setTextColor(resources.getColor(R.color.forgotresidentcode))
        title.textSize = 16F
        title.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")

        alertDialog.setCustomTitle(title)

        //message
        var message = SpannableString("請確認您輸入的 iPad 住戶代碼正確無誤，再試一次吧！")
            message.setSpan(ForegroundColorSpan(resources.getColor(R.color.Choosecommunity)),0,message.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        alertDialog.setMessage(message)
        
        dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog)
        dialog.show()
        dialog.window?.setLayout((widthPixels*0.778).toInt(), (heightPixels*0.281).toInt())
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(resources.getString(R.color.loading)))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")
    }

    //住戶代碼已被其他裝置綁定
    @SuppressLint("ResourceType")
    fun userCodeUsing(){
        alertDialog = AlertDialog.Builder(this)
            .setPositiveButton("好", null)   // listener argument can write some action

        // title
        var title = TextView(this)
        title.text = "住戶代碼已被其他裝置綁定"
        title.setTextColor(resources.getColor(R.color.forgotresidentcode))
        title.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")
        title.textSize = 16F

        alertDialog.setCustomTitle(title)

        //message
        var message = SpannableString("若您需要更換新的裝置，請洽詢社區管理中心協助解除綁定，並重新登入即可繼續此服務。")
        message.setSpan(ForegroundColorSpan(resources.getColor(R.color.Choosecommunity)),0,message.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        alertDialog.setMessage(message)

        dialog = alertDialog.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog)
        dialog.show()
        dialog.window?.setLayout((widthPixels*0.778).toInt(), (heightPixels*0.313).toInt())
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(resources.getString(R.color.loading)))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")

    }


    fun forgot(view: View){
        setContentView(R.layout.forgotresidentcode)
        textview7.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Regular.otf")
    }

    fun privacypolicy(view: View){
        setContentView(R.layout.privacypolicy)
        val handler = Handler()
        handler.postDelayed({
            setContentView(R.layout.privacypolicy1)
            privacy.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Regular.otf")
        }, 2000)
    }

    // login and start MainActivity
    @SuppressLint("ResourceAsColor")
    fun landingClick(view: View) {
        val button = findViewById<Button>(R.id.landingbutton)
        landingbutton3.typeface = Typeface.createFromAsset(assets,"NotoSansTC-Medium.otf")

        if (landingedit.text.isNullOrEmpty() && landingbutton3.text.isNullOrEmpty()) {
            landingedit.setBackgroundResource(R.drawable.error_border)
            userCodeErrorDialog()
        } else {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, intent)
            }
            startActivity(intent)
        }

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
        initLandingPage()
    }

    fun close_view(view: View){
        setContentView(R.layout.activity_login)
    }

    //點擊空白處關閉鍵盤
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (this@loginActivity.currentFocus != null) {
                if (this@loginActivity.currentFocus!!.windowToken != null) {
                    imm.hideSoftInputFromWindow(
                        this@loginActivity.currentFocus!!.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            }
        }
        return super.onTouchEvent(event)
    }
}

















