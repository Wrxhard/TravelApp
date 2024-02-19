package com.wrxhard.ftravel.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.wrxhard.ftravel.R

class LocationAdapter(val context: Context,val list: List<String>, val onclick: (String)-> Unit) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rootView = LayoutInflater.from(context).inflate(R.layout.spinner_item,parent, false)
        val textView = rootView.findViewById<android.widget.TextView>(R.id.spinner_item_text)
        textView.text = list[position]
        return rootView
    }
}