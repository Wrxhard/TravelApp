package com.wrxhard.ftravel.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivitySignUpBinding
import com.wrxhard.ftravel.view_model.activity.AuthActivityViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val authViewModel: AuthActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        binding.backBtn.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
    }
}