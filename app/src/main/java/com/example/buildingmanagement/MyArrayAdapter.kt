package com.example.buildingmanagement

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

class MyArrayAdapter(context: Context,
                     @LayoutRes private val layoutResource: Int,
                     private val values: List<String>): ArrayAdapter<String>(context, layoutResource, values){

    val typeface: Typeface = Typeface.createFromAsset(context.assets, "NotoSansTC-Medium.otf")


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var item: TextView = super.getView(position, convertView, parent) as TextView

        item.setTypeface(typeface)

        return item
    }

}