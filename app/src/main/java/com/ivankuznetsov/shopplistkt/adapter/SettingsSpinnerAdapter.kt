package com.ivankuznetsov.shopplistkt.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.ivankuznetsov.shopplistkt.R


class SettingsSpinnerAdapter(val appContext : Context,
                             val resource : Int,
                             val colors : List<Int>)
    : ArrayAdapter<Int>(appContext, resource, colors) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position,convertView,parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position,convertView,parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup) : View{
        val view : View = View.inflate(appContext, R.layout.spinner_item, null)
        val imageview : ImageView = view.findViewById(R.id.spinner_image)
        imageview.setImageResource(colors[position])
        return view
    }

}