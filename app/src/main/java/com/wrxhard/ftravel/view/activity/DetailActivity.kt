package com.wrxhard.ftravel.view.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityDetailBinding
import com.wrxhard.ftravel.model.base_model.list_item.Food
import com.wrxhard.ftravel.model.generic_model.list_item.Item
import com.wrxhard.ftravel.util.LayoutHelper
import com.wrxhard.ftravel.view.adapter.DetailAdapter
import com.wrxhard.ftravel.view.adapter.LandingAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var pageListener: ViewPager2.OnPageChangeCallback

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LayoutHelper.hideSystem(this)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val backgrounds = listOf(
            R.drawable.landing_bg_1,
            R.drawable.landing_bg_2,
            R.drawable.landing_bg_3
        )
        val foodList = listOf(
            Item(Food("1", "Pizza", "Delicious pizza", "10$", emptyList())),
            Item(Food("2", "Burger", "Tasty burger", "8$", emptyList())),
            Item(Food("3", "Pasta", "Italian pasta", "12$", emptyList()))
        )
        binding.foodPrice.visibility = ImageView.GONE
        binding.locationBtn.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        /// direction to location
        binding.btnDirection.setOnClickListener {
            val intent = Intent(this, MapDirectionActivity::class.java)
            startActivity(intent)
        }

        setupRemmendation(foodList)
        LayoutHelper.blurView(this,binding.root,binding.btnDirection,10f)
        setUpViewPager(backgrounds)
        setContentView(binding.root)

    }

    private fun setupRemmendation(list: List<Item<*>>){
        binding.recommendation.adapter = DetailAdapter(list){

        }
        binding.recommendation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


    }
    private fun setUpViewPager(backgrounds: List<Int> ){

        binding.viewPagerDetail.adapter = LandingAdapter(backgrounds)
        val slidedot= binding.slideDotDetail
        val dotsimg = Array(backgrounds.size){
            ImageView(this)
        }

        dotsimg.forEach {
            it.setImageResource(R.drawable.detail_dot_selected)
            slidedot.addView(it, LayoutHelper.slideParams)
        }
        dotsimg[0].setImageResource(R.drawable.detail_dot_selected)

        pageListener = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                dotsimg.mapIndexed { index, imageView ->
                    if (index == position){
                        imageView.setImageResource(R.drawable.detail_dot_selected)
                    }else{
                        imageView.setImageResource(R.drawable.detail_dot_unselected)
                    }
                }
                super.onPageSelected(position)
            }
        }

        binding.viewPagerDetail.registerOnPageChangeCallback(pageListener)

        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.CREATED)
            {
                while (isActive)
                {
                    delay(2000)
                    withContext(Dispatchers.Main){
                        val nextBg = (binding.viewPagerDetail.currentItem + 1)
                        binding.viewPagerDetail.setCurrentItem(nextBg % backgrounds.size, true)
                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.viewPagerDetail.unregisterOnPageChangeCallback(pageListener)
    }

}