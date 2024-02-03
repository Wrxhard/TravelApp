package com.wrxhard.ftravel.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        binding.backBtn.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}