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
import androidx.viewpager2.widget.ViewPager2
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityLandingBinding
import com.wrxhard.ftravel.util.LayoutHelper
import com.wrxhard.ftravel.view.adapter.LandingAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    private lateinit var pageListener: ViewPager2.OnPageChangeCallback
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingBinding.inflate(layoutInflater)

        LayoutHelper.hideSystem(this)

        val backgrounds = listOf(
            R.drawable.landing_bg_02,
            R.drawable.landing_bg_01,
            R.drawable.landing_bg_03
        )
        setUpViewPager(backgrounds)

        binding.getStartedBtn.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
        LayoutHelper.blurView(this, binding.root, binding.blurView, 10f)
        setContentView(binding.root)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(pageListener)
    }

    private fun setUpViewPager(backgrounds: List<Int> ){

        binding.viewPager.adapter = LandingAdapter(backgrounds)
        val slidedot= binding.slideDot
        val dotsimg = Array(backgrounds.size){
            ImageView(this)
        }

        dotsimg.forEach {
            it.setImageResource(R.drawable.dot_unselected)
            slidedot.addView(it,LayoutHelper.slideParams)
        }
        dotsimg[0].setImageResource(R.drawable.dot_selected)

        pageListener = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                dotsimg.mapIndexed { index, imageView ->
                    if (index == position){
                        imageView.setImageResource(R.drawable.dot_selected)
                    }else{
                        imageView.setImageResource(R.drawable.dot_unselected)
                    }
                }
                super.onPageSelected(position)
            }
        }

        binding.viewPager.registerOnPageChangeCallback(pageListener)

        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.CREATED)
            {
                while (isActive)
                {
                    delay(3000)
                    withContext(Dispatchers.Main){
                        val nextBg = (binding.viewPager.currentItem + 1)
                        binding.viewPager.setCurrentItem(nextBg % backgrounds.size, true)
                    }
                }
            }
        }
    }

}