package com.wrxhard.ftravel.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityDetailBinding
import com.wrxhard.ftravel.util.LayoutHelper
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
        setContentView(binding.root)
        val backgrounds = listOf(
            R.drawable.landing_bg_1,
            R.drawable.landing_bg_2,
            R.drawable.landing_bg_3
        )
        setUpViewPager(backgrounds)
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
        dotsimg[0].setImageResource(R.drawable.dot_selected)

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