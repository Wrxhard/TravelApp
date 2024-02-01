package com.wrxhard.ftravel.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.FragmentHomeBinding
import com.wrxhard.ftravel.model.base_model.Food
import com.wrxhard.ftravel.model.base_model.Location
import com.wrxhard.ftravel.model.generic_model.Item
import com.wrxhard.ftravel.view.adapter.ItemAdapter
import com.wrxhard.ftravel.view_model.fragment.HomeViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val list  = listOf(
            Item(Location("1","","Nha Tho Duc Ba","Ho Chi Minh")),
            Item(Location("2","","Nha Tho Duc Ba 2","Ha Noi"))
        )
        val adapter = ItemAdapter(list,{

        })
        binding.cardList.adapter = adapter
        binding.cardList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}