package com.wrxhard.ftravel.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityLoginBinding
import com.wrxhard.ftravel.util.SystemHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemHelper.hideSystem(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.backBtn.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}