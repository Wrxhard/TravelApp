package com.wrxhard.ftravel.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.model.base_model.Food
import com.wrxhard.ftravel.model.base_model.Location
import com.wrxhard.ftravel.model.generic_model.Item

class ItemAdapter(val listItem: List<Item<*>>, val onClick:(Item<*>)->Unit): RecyclerView.Adapter<ItemAdapter.ItemMyViewHolder>(){
    inner class ItemMyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(Item: Item<*>, onClick: (Item<*>) -> Unit)
        {
           when(Item.data){
               is Food ->{
                   val food=Item.data
                   val desc = view.findViewById<TextView>(R.id.card_desc)
                   val location = view.findViewById<TextView>(R.id.card_location)
                   desc.text = food.name
                   location.text = food.locations[0].address
               }
               is Location -> {
                   val location = Item.data
                   val desc = view.findViewById<TextView>(R.id.card_desc)
                   val address = view.findViewById<TextView>(R.id.card_location)
                   desc.text = location.name
                   address.text = location.address
               }
               else -> {
                   Log.e("ItemAdapter", "Unknown Item Type")
               }
           }
            view.setOnClickListener{onClick(Item)}

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem=layoutInflater.inflate(R.layout.card,parent,false)
        return ItemMyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ItemMyViewHolder, position: Int) {
        val film=listItem[position]
        holder.bind(film,onClick)

    }


    override fun getItemCount(): Int {
        return listItem.size
    }
}