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
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityLoginBinding
import com.wrxhard.ftravel.util.Event
import com.wrxhard.ftravel.util.SystemHelper
import com.wrxhard.ftravel.view_model.activity.AuthActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemHelper.hideSystem(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.SignInBtn.setOnClickListener {
            val username = binding.usernameTxtfield.text.toString()
            val password = binding.passwordTxtfield.text.toString()
            authViewModel.login(username, password)
        }
        watchState()
        setContentView(binding.root)
    }
    private fun watchState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                authViewModel.loginevent.collectLatest {
                    resp ->
                    when(resp){
                        is Event.Loading -> {
                            //TODO: Implement loading behavior
                        }
                        is Event.Success -> {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        is Event.Failure -> {
                            Toast.makeText(this@LoginActivity, resp.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}