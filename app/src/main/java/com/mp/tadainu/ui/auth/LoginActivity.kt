package com.mp.tadainu.ui.auth

import BaseActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.mp.tadainu.Fragment_Home
import com.mp.tadainu.api.RetrofitInstance
import com.mp.tadainu.api.auth.ApiService
import com.mp.tadainu.api.auth.request.LoginRequest
import com.mp.tadainu.api.auth.response.ApiResponse
import com.mp.tadainu.databinding.LoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity()  {
    private lateinit var binding: LoginBinding
    private lateinit var toolbar: Toolbar
    private lateinit var editTextLoginId: EditText
    private lateinit var editTextLoginPassword: EditText
    private lateinit var textViewNotFound: TextView
    private lateinit var buttonLoginSearchAccount: Button
    private lateinit var buttonLoginComplete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = LoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize toolbar
        toolbar = binding.toolbarLogin // Use binding to initialize toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button
        supportActionBar?.title = "로그인"

        // Initialize other UI elements using binding
        editTextLoginId = binding.editTextLoginId
        editTextLoginPassword = binding.editTextLoginPassword
        buttonLoginComplete = binding.buttonLoginComplete
        textViewNotFound = binding.textViewNotFound
        textViewNotFound.visibility = View.INVISIBLE

        // Set a click listener for the "로그인하기" button
        buttonLoginComplete.setOnClickListener {
            val enteredId = editTextLoginId.text.toString()
            val enteredPassword = editTextLoginPassword.text.toString()
            val request = LoginRequest(enteredId, enteredPassword)
            isValidLogin(request)
        }
    }

    // You can implement your own login validation and authentication logic here
    private fun isValidLogin(request:LoginRequest) {
        val service = RetrofitInstance.retrofit.create(ApiService::class.java)
        service.signIn(request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val saveInfoResponse = response.body()
                if (response.isSuccessful) {
                    // Handle a successful response from the server
                    // You can show a success message or navigate to the next screen
                    val intent = Intent(this@LoginActivity, Fragment_Home::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "로그인 성공했습니다.", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@LoginActivity, "로그인 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                    textViewNotFound.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle network request failure
                Log.e("NetworkError", "Network request failed: ${t.message}", t)
            }
        })
    }
}