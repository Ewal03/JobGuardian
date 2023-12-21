package com.example.jobguardian.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.jobguardian.R
import com.example.jobguardian.ui.authenticaion.WelcomeActivity
import com.example.jobguardian.ui.main.view.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splash: ImageView = findViewById(R.id.iv_splash)
        splash.alpha = 0f
        splash.animate().setDuration(1250).alpha(1f).withEndAction {
            val intent = Intent(this,WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()


        }
        supportActionBar?.hide()
    }
}