package com.wrxhard.ftravel.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrxhard.ftravel.databinding.FragmentLocationBinding
import com.wrxhard.ftravel.model.base_model.list_item.Location
import com.wrxhard.ftravel.model.generic_model.list_item.Item
import com.wrxhard.ftravel.view.activity.DetailActivity
import com.wrxhard.ftravel.view.adapter.ItemAdapter

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        val locations = listOf(
            Item(Location("1", "https://www.google.com","Nho tho duc ba", "10000$")),
            Item(Location("2", "https://www.google.com","Nho tho duc ba", "10000$"))
        )
        binding.locationRecyclerView.adapter = ItemAdapter(locations){
            val intent = Intent(this@LocationFragment.activity, DetailActivity::class.java)
            startActivity(intent)
        }
        binding.locationRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

}