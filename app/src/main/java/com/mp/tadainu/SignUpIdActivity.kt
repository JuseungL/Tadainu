package com.mp.tadainu

import BaseActivity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.mp.tadainu.api.RetrofitInstance
import com.mp.tadainu.api.auth.ApiService
import com.mp.tadainu.api.auth.request.CheckIdRequest
import com.mp.tadainu.api.auth.response.ApiResponse
import com.mp.tadainu.databinding.SignupIdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpIdActivity : BaseActivity() {
    private lateinit var binding: SignupIdBinding // View Binding object declaration
    private lateinit var buttonGoToNickName: Button // Define the button in class scope
    private lateinit var buttonCheckId: Button
    private lateinit var editTextId: EditText // Define editTextId here
    private lateinit var editTextPassword: EditText // Define the TextView
    private lateinit var editTextPasswordCheck: EditText
    private lateinit var textViewCheckID: TextView // Define the TextView
    private lateinit var textViewNotSamePassword: TextView

    //비미런호일치
    private lateinit var passwordTextWatcher: TextWatcher
    private var passwordsMatch = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = SignupIdBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarSignUpId)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button
        supportActionBar?.title = "회원가입"


        buttonCheckId = binding.buttonCheckId
        buttonGoToNickName = binding.buttonGoToNickName
        editTextId = binding.editTextId
        textViewCheckID = binding.textViewCheckID // Initialize the TextView
        editTextPassword = binding.editTextPassword
        editTextPasswordCheck = binding.editTextPasswordCheck
        textViewNotSamePassword = binding.textViewNotSamePassword
        buttonGoToNickName.visibility = View.INVISIBLE
        // Initially, make the TextView invisible
        textViewCheckID.visibility = View.INVISIBLE

        buttonCheckId.setOnClickListener {
            val userIdToCheck = editTextId.text.toString()
            checkId(userIdToCheck)
        }

        passwordTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = editTextPassword.text.toString()
                val passwordConfirm = editTextPasswordCheck.text.toString()

                // Check if passwords match
                passwordsMatch = password == passwordConfirm

                if (passwordsMatch) {
                    textViewNotSamePassword.visibility = View.VISIBLE // Show error message
                    textViewNotSamePassword.text = "비밀번호가 일치합니다."
                    buttonGoToNickName.visibility = View.VISIBLE // Show the "다음으로 넘어가기" button
                } else {
                    textViewNotSamePassword.visibility = View.VISIBLE // Show error message
                    textViewNotSamePassword.text = "비밀번호가 일치하지 않습니다."
                    buttonGoToNickName.visibility = View.INVISIBLE // Hide the button
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        editTextPassword.addTextChangedListener(passwordTextWatcher)
        editTextPasswordCheck.addTextChangedListener(passwordTextWatcher)

        binding.buttonGoToNickName.setOnClickListener {
            // Switch to signup_nickname activity
            if (textViewCheckID.text == "사용가능한 아이디입니다.") {
                val intent = Intent(this, SignUpNicknameActivity::class.java)
                intent.putExtra("usr_id", editTextId.text.toString())
                intent.putExtra("usr_pwd", editTextPassword.text.toString())// Replace "key" and "value" with your data
                startActivity(intent)
            } else if (textViewCheckID.text == "이미 존재하는 아이디입니다."){
                // Show an AlertDialog or a Toast message here to notify the user
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("이미 존재하는 아이디입니다.")
                alertDialogBuilder.setMessage("이 사용자 ID는 이미 사용되었습니다. \n다른 ID를 선택하십시오.")
                alertDialogBuilder.create().show()
            } else {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("ID 중복 확인을 해주세요.")
                alertDialogBuilder.setMessage("ID 중복확인 후 다음으로 넘어갈 수 있습니다.")
                alertDialogBuilder.create().show()
            }
        }

    }

    private fun checkId(userId: String) {
        val request = CheckIdRequest(userId)
        val service = RetrofitInstance.retrofit.create(ApiService::class.java)
        // Make a network call using the service
        service.checkId(request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                if (response.isSuccessful) {
                    // 서버 응답이 200일 때
                    textViewCheckID.visibility = View.VISIBLE // 보이기
                    textViewCheckID.text = "사용가능한 아이디입니다."
                } else {
                    // 서버 응답이 400일 때
                    textViewCheckID.visibility = View.VISIBLE // 보이기
                    textViewCheckID.text = "이미 존재하는 아이디입니다." // 텍스트 설정
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // 네트워크 요청 실패 처리
                Log.e("NetworkError", "Network request failed: ${t.message}", t)
            }
        })
    }
}
