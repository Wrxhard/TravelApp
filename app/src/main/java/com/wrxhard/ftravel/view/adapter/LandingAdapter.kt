package com.wrxhard.ftravel.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.wrxhard.ftravel.R

class LandingAdapter(private val listBackground: List<Int>): RecyclerView.Adapter<LandingAdapter.BackgroundMyViewHolder>(){
    inner class BackgroundMyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(Background: Int)
        {
            val background = view.findViewById<ImageView>(R.id.landing_background_img)
            background.setImageResource(Background)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackgroundMyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listBackground=layoutInflater.inflate(R.layout.landing_background,parent,false)
        return BackgroundMyViewHolder(listBackground)
    }

    override fun getItemCount(): Int {
        return listBackground.size
    }

    override fun onBindViewHolder(holder: BackgroundMyViewHolder, position: Int) {
        val bg=listBackground[position]
        holder.bind(bg)
    }

}