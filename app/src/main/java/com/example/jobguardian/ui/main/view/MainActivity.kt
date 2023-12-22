package com.example.jobguardian.ui.main.view

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jobguardian.R
import com.example.jobguardian.databinding.ActivityMainBinding
import com.example.jobguardian.ui.main.SharedViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        val navView: BottomNavigationView = binding.navView
        val userId = intent.getStringExtra("userId")
        if (userId != null) {
            sharedViewModel.userId.value = userId
        }
        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_detection, R.id.navigation_profile,  R.id.navigation_favorit
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
