package com.example.jobguardian.ui.authenticaion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobguardian.R
import com.example.jobguardian.databinding.ActivityWelcomeBinding
import com.example.jobguardian.ui.authenticaion.signIn.SignInActivity
import com.example.jobguardian.ui.authenticaion.signUp.SignUpActivity
import com.example.jobguardian.ui.main.view.MainActivity
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var auth: FirebaseAuth

//    override fun onStart() {
//        super.onStart()
//        if(auth.currentUser != null){
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//    }

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