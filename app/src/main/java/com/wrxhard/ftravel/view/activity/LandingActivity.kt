package com.wrxhard.ftravel.view.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityLandingBinding
import com.wrxhard.ftravel.util.SystemHelper
import com.wrxhard.ftravel.view.adapter.LandingAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    private lateinit var pageListener: ViewPager2.OnPageChangeCallback
    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemHelper.hideSystem(this)

        val backgrounds = listOf(
            R.drawable.landing_bg_1,
            R.drawable.landing_bg_2,
            R.drawable.landing_bg_3
        )
        setUpViewPager(backgrounds)

        binding.getStartedBtn.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
        SystemHelper.blurView(this, binding.root, binding.blurView, 10f)
        setContentView(binding.root)
    }


    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(pageListener)
    }

    private fun setUpViewPager(backgrounds: List<Int> ){
        binding = ActivityLandingBinding.inflate(layoutInflater)

        binding.viewPager.adapter = LandingAdapter(backgrounds)
        val slidedot= binding.slideDot
        val dotsimg = Array(backgrounds.size){
            ImageView(this)
        }

        dotsimg.forEach {
            it.setImageResource(R.drawable.dot_unselected)
            slidedot.addView(it,params)
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