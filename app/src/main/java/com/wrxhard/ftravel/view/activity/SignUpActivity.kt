package com.wrxhard.ftravel.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wrxhard.ftravel.databinding.ActivitySignUpBinding
import com.wrxhard.ftravel.util.Event
import com.wrxhard.ftravel.util.LayoutHelper
import com.wrxhard.ftravel.view_model.activity.AuthActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val authViewModel: AuthActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        LayoutHelper.hideSystem(this)
        binding.LoginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.nextButton.setOnClickListener {
            if (binding.checkbox.isChecked)
            {
                val username = binding.usernameTxtfield.text.toString()
                val password = binding.passwordTxtfield.text.toString()
                authViewModel.register(username,password)
            }
        }
        setContentView(binding.root)
        watchState()
    }
    private fun watchState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                authViewModel.registerevent.collectLatest {
                    resp ->
                    when(resp){
                        is Event.Loading -> {
                            //TODO: Implement loading behavior
                        }
                        is Event.Success -> {
                            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        is Event.Failure -> {
                            Toast.makeText(this@SignUpActivity, resp.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}