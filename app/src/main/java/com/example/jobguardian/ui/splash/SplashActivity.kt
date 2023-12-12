//package com.example.jobguardian.ui.splash
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.ImageView
//import android.widget.TextView
//import com.example.jobguardian.R
//import com.example.jobguardian.ui.authenticaion.WelcomeActivity
//
//class SplashActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//
//        val splash: TextView = findViewById(R.id.iv)
//        splash.alpha = 0f
//        splash.animate().setDuration(1250).alpha(1f).withEndAction {
//            val intent = Intent(this,WelcomeActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
//            finish()
//        }
//    }
//}