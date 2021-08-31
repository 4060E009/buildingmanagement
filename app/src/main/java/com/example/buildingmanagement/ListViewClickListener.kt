package com.example.buildingmanagement

import android.view.View
import android.widget.AdapterView

private var listView: View? = null

class ListViewClickListener : AdapterView.OnItemClickListener {
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(listView!=null){
            listView!!.setBackgroundColor(listView!!.getResources().getColor(R.color.loading))
        }
        p1?.setBackgroundColor(p1.getResources().getColor(R.color.loading))
        listView=p1
    }



}