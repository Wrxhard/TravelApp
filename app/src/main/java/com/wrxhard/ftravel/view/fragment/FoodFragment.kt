package com.wrxhard.ftravel.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrxhard.ftravel.databinding.FragmentFoodBinding
import com.wrxhard.ftravel.model.base_model.list_item.Food
import com.wrxhard.ftravel.model.generic_model.list_item.Item
import com.wrxhard.ftravel.view.activity.DetailActivity
import com.wrxhard.ftravel.view.adapter.ItemAdapter

class FoodFragment : Fragment() {

    private lateinit var binding: FragmentFoodBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFoodBinding.inflate(inflater, container, false)
        val foods = listOf(
            Item(Food("1", "Pizza", "https://www.google.com", "10000$", emptyList())),
            Item(Food("2", "Pizza", "https://www.google.com", "10000$", emptyList()))
        )
        binding.foodRecyclerView.adapter = ItemAdapter(foods){
            val intent = Intent(this@FoodFragment.activity, DetailActivity::class.java)
            startActivity(intent)
        }
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

}