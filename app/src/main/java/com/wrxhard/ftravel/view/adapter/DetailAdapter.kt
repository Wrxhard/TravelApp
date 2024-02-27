package com.wrxhard.ftravel.view.adapter

import com.wrxhard.ftravel.model.generic_model.list_item.Item
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.model.base_model.list_item.Food
import com.wrxhard.ftravel.model.base_model.list_item.Location

class DetailAdapter(private val listDetailItem: List<Item<*>>, private val onClick:(Item<*>)->Unit): RecyclerView.Adapter<DetailAdapter.DetailItemMyViewHolder>(){
    inner class DetailItemMyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(item: Item<*>, onClick: (Item<*>) -> Unit)
        {
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
                    Log.e("DetailItemAdapter", "Unknown DetailItem Type")
                }
            }
            view.setOnClickListener{onClick(item)}

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailItemMyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listDetailItemView = if (listDetailItem[0].data is Location) {
            layoutInflater.inflate(R.layout.detail_location_card,parent,false)
        } else {
            layoutInflater.inflate(R.layout.food_card,parent,false)
        }
        return DetailItemMyViewHolder(listDetailItemView)
    }


    override fun onBindViewHolder(holder: DetailItemMyViewHolder, position: Int) {
        val item=listDetailItem[position]
        holder.bind(item,onClick)

    }


    override fun getItemCount(): Int {
        return listDetailItem.size
    }
}