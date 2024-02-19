package com.wrxhard.ftravel.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.model.base_model.Category

class CategoryAdapter(private val listCategory: List<Category>, private val onclick: (Category) -> Unit): RecyclerView.Adapter<CategoryAdapter.CategoryMyViewHolder>(){
    inner class CategoryMyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(category: Category,onclick: (Category) -> Unit)
        {
            val icon = view.findViewById<ImageView>(R.id.categoryIcon)
            icon.setImageResource(category.icon)
            val name = view.findViewById<MaterialTextView>(R.id.categoryName)
            name.text = category.name
            view.setOnClickListener{
                onclick(category)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listCategory=layoutInflater.inflate(R.layout.category_layout,parent,false)
        return CategoryMyViewHolder(listCategory)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    override fun onBindViewHolder(holder: CategoryMyViewHolder, position: Int) {
        val category=listCategory[position]
        holder.bind(category,onclick)
    }

}