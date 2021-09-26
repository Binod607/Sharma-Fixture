package com.example.sharmafixture.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.sharmafixture.R
import com.example.sharmafixture.databinding.ActivitySignupBinding
import com.example.sharmafixture.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_signup)
        binding.lifecycleOwner=this
        binding.alreadyHaveAnAccount.setOnClickListener(){
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}