package com.mp.tadainu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.mp.tadainu.databinding.ActivityMainBinding
import com.mp.tadainu.ui.auth.LoginActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Declare a View Binding object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the View Binding object
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Access your UI components through the binding object
        binding.buttonLoginMainLogin.setOnClickListener {
            // Switch to signup_nickname activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLoginMainSignUp.setOnClickListener {
            // Switch to signup_nickname activity
            val intent = Intent(this, SignUpIdActivity::class.java)
            startActivity(intent)
        }
    }
}
