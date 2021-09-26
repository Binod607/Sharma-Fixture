package com.example.sharmafixture.login

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.sharmafixture.R
import com.example.sharmafixture.data.model.User
import com.example.sharmafixture.data.repository.UserRepository
import com.example.sharmafixture.databinding.ActivityLoginBinding
import com.example.sharmafixture.home.Bottom_Nav_Activity
import com.example.sharmafixture.signup.SignupActivity
import com.example.sharmainteriordesign.api.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        if (!hasPermission()) {
            requestPermission()
        }
        binding.signUp.setOnClickListener() {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        binding.login.setOnClickListener() {
            var user = User(
                email = binding.email.text.toString(),
                password = binding.password.text.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                val repository = UserRepository()
                val response = repository.checkUSer(user)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token!!}"
                    ServiceBuilder.id = response.id
                    ServiceBuilder.name=response.data!!.get(0).name
                    val data = response.data
                    ServiceBuilder.name = data?.get(0)?.name
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "${ServiceBuilder.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@LoginActivity, Bottom_Nav_Activity::class.java))

                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Inavalid User", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }

    }
    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@LoginActivity,
            permissions, 876
        )
    }
    fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }
}