package com.wrxhard.ftravel.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.model.base_model.list_item.Food
import com.wrxhard.ftravel.model.base_model.list_item.Location
import com.wrxhard.ftravel.model.generic_model.list_item.Item

class ItemAdapter(private val listItem: List<Item<*>>, private val onClick:(Item<*>)->Unit): RecyclerView.Adapter<ItemAdapter.ItemMyViewHolder>(){
    inner class ItemMyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(item: Item<*>, onClick: (Item<*>) -> Unit,  position: Int)
        {
            if (position == listItem.size - 1) {
                val params = view.layoutParams as ViewGroup.MarginLayoutParams
                params.bottomMargin = 280
                view.layoutParams = params
            }
           when(item.data){
               is Food ->{
                   val food=item.data
                   val name = view.findViewById<TextView>(R.id.food_name)
                   name.text = food.name
                   /*Glide
                       .with(view)
                       .load(food.image_url)
                       .into(view.findViewById(R.id.card_img))*/
               }
               is Location -> {
                   val location = item.data
                   val name = view.findViewById<TextView>(R.id.location_name)
                   name.text = location.name
                   /*Glide
                       .with(view)
                       .load(location.image_url)
                       .into(view.findViewById(R.id.card_img))*/
               }
               else -> {
                   Log.e("ItemAdapter", "Unknown Item Type")
               }
           }
            view.setOnClickListener{onClick(item)}

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItemView = if (listItem[0].data is Location) {
            layoutInflater.inflate(R.layout.location_card,parent,false)
        } else {
            layoutInflater.inflate(R.layout.food_card,parent,false)
        }
        return ItemMyViewHolder(listItemView)
    }

    override fun onBindViewHolder(holder: ItemMyViewHolder, position: Int) {
        val item=listItem[position]
        holder.bind(item,onClick, position)

    }


    override fun getItemCount(): Int {
        return listItem.size
    }
}