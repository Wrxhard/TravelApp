package com.wrxhard.ftravel.view.activity

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.wrxhard.ftravel.databinding.ActivityAuthBinding
import com.wrxhard.ftravel.util.Event
import com.wrxhard.ftravel.util.LayoutHelper
import com.wrxhard.ftravel.view_model.activity.AuthActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val REQUEST_CODE_GOOGLE_SIGN_IN = 687 /* unique request id */
    private lateinit var binding: ActivityAuthBinding
    private val authViewModel: AuthActivityViewModel by viewModels()
    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credential = Identity.getSignInClient(this).getSignInCredentialFromIntent(result.data)
                val id = credential.id
                val password = id.hashCode().toString()
                authViewModel.register(id, password)
            } catch (e: ApiException) {
                Log.e("Api Exception", "Google Sign-in failed", e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hideSystemBar
        LayoutHelper.hideSystem(this)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        binding.LoginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        binding.SignUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.goolgeBtn.setOnClickListener {
            //signInWithGoogle()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        watchState()
        setContentView(binding.root)
    }
    private fun signInWithGoogle() {
        val request = GetSignInIntentRequest.builder()
            .setServerClientId(getString(com.wrxhard.ftravel.R.string.web_client_id))
            .build()
        Identity.getSignInClient(this@AuthActivity)
            .getSignInIntent(request)
            .addOnSuccessListener { result: PendingIntent ->
                val intentSenderRequest = IntentSenderRequest.Builder(result.intentSender).build()
                googleSignInLauncher.launch(intentSenderRequest)
            }
            .addOnFailureListener { e: Exception? ->
                Log.e("Google Login", "Google Sign-in failed", e)
            }
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
                            val intent = Intent(this@AuthActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        is Event.Failure -> {
                            Toast.makeText(this@AuthActivity, resp.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
                try {
                    val credential =
                        Identity.getSignInClient(this).getSignInCredentialFromIntent(data)
                    val id = credential.id
                    val password=id.hashCode().toString()
                    authViewModel.loginWithGoogle(id, password)


                } catch (e: ApiException) {
                    Log.e(
                        "Api Exception",
                        "Google Sign-in failed",
                        e
                    )
                }
            }
        }
    }
}