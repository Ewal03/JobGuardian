package com.example.jobguardian.ui.authenticaion.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobguardian.R
import com.example.jobguardian.databinding.ActivitySignInBinding
import com.example.jobguardian.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}