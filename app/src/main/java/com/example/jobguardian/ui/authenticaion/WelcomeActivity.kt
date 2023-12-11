package com.example.jobguardian.ui.authenticaion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobguardian.R
import com.example.jobguardian.databinding.ActivityWelcomeBinding
import com.example.jobguardian.ui.authenticaion.signIn.SignInActivity
import com.example.jobguardian.ui.authenticaion.signUp.SignUpActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}