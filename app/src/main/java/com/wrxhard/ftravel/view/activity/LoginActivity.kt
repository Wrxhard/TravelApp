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
import com.wrxhard.ftravel.databinding.ActivityLoginBinding
import com.wrxhard.ftravel.util.Event
import com.wrxhard.ftravel.util.SystemHelper
import com.wrxhard.ftravel.view_model.activity.AuthActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemHelper.hideSystem(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.nextButton.setOnClickListener {
            val username = binding.emailTxtfield.text.toString()
            val password = binding.passwordTxtfield.text.toString()
            authViewModel.login(username, password)
        }
        binding.RegisterBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
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