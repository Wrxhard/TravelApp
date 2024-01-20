package com.wrxhard.ftravel.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.model.generic_model.Item
import com.wrxhard.ftravel.model.generic_model.Section

class SectionAdapter(val listSection:List<Section>, val onClick:(Item<*>)->Unit):RecyclerView.Adapter<SectionAdapter.SectionMyViewHolder>() {
    inner class SectionMyViewHolder(val view:View):RecyclerView.ViewHolder(view){

        @SuppressLint("NotifyDataSetChanged")
        fun bind(Section:Section, onClick: (Item<*>) -> Unit){
            val title=view.findViewById<TextView>(R.id.Title)
            title.text=Section.title
            val section=view.findViewById<RecyclerView>(R.id.ItemList)
            val itemAdapter= Section.items?.let { ItemAdapter(it,onClick) }
            section.adapter=itemAdapter
            section.layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL,false)
            itemAdapter?.notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionMyViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val listItem=layoutInflater.inflate(R.layout.section,parent,false)
        return SectionMyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: SectionMyViewHolder, position: Int) {
        val mySection=listSection[position]
        holder.bind(mySection,onClick)
    }

    override fun getItemCount(): Int {
        return listSection.size
    }
}