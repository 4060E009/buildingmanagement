package com.example.buildingmanagement

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.AlarmClock
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class loginActivity : AppCompatActivity() {
    private val TAG = "loginActivity"

    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    val array = arrayListOf(
        "社區1",
        "社區2",
        "社區3",
        "社區4",
        "社區5",
        "社區6",
        "社區7",
        "社區8",
        "社區9",
        "社區10",
        "社區11",
        "社區12",
        "社區13",
        "社區14",
        "社區15"
    )

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

    fun openDialog(view: View) {
        alertDialog = AlertDialog.Builder(this)
        val rowList: View = layoutInflater.inflate(R.layout.my_dialog, null)
        listView = rowList.findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        dialog = alertDialog.create()
//        dialog.setTitle("便當")
        dialog.show()
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






