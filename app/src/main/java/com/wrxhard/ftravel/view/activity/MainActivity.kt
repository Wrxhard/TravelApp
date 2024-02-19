package com.wrxhard.ftravel.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityMainBinding
import com.wrxhard.ftravel.model.base_model.Category
import com.wrxhard.ftravel.util.SystemHelper
import com.wrxhard.ftravel.view.adapter.CategoryAdapter
import com.wrxhard.ftravel.view.adapter.HomeVPAdapter
import com.wrxhard.ftravel.view.adapter.LocationAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        //bindingview
        binding = ActivityMainBinding.inflate(layoutInflater)
        //hideSystemBar
        SystemHelper.hideSystem(this)

        val categories = listOf(
            Category(R.drawable.ic_lightbulb, "Suggest"),
            Category(R.drawable.ic_location, "Place"),
            Category(R.drawable.ic_calendar, "Schedule"),
        )
        setupCategoryList(categories)

        val userLocations = listOf(
            "Ho Chi Minh",
            "Ha Noi",
        )
        setUpDropdown(userLocations)

        setupTabLayout()

        SystemHelper.blurView(this,binding.root,binding.blurView,10f)

        setContentView(binding.root)

    }

    private fun setUpDropdown(userLocations: List<String>){
        binding.LocationDropdown.adapter = LocationAdapter(this, userLocations){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCategoryList(categories: List<Category>)
    {
        binding.categoryList.adapter = CategoryAdapter(categories){
                category->
            Toast.makeText(this,category.name,Toast.LENGTH_SHORT).show()
        }
        binding.categoryList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setupTabLayout(){
        binding.viewPager.adapter = HomeVPAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Location"
                1 -> tab.text = "Food"
            }
        }.attach()
    }
}