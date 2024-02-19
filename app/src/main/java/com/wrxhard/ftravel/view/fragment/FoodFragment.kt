package com.wrxhard.ftravel.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrxhard.ftravel.databinding.FragmentFoodBinding
import com.wrxhard.ftravel.model.base_model.Food
import com.wrxhard.ftravel.model.generic_model.Item
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
        }
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

}