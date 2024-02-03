package com.wrxhard.ftravel.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityAuthBinding
import com.wrxhard.ftravel.util.SystemHelper

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hideSystemBar
        SystemHelper.hideSystem(this)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        binding.LoginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.SignUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }
}