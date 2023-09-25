package com.mp.tadainu

import BaseActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.mp.tadainu.api.RetrofitInstance
import com.mp.tadainu.api.auth.ApiService
import com.mp.tadainu.api.auth.request.CheckNicknameRequest
import com.mp.tadainu.api.auth.request.SaveUserInfoRequest
import com.mp.tadainu.api.auth.response.ApiResponse
import com.mp.tadainu.databinding.SignupNicknameBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpNicknameActivity : BaseActivity() {
    private lateinit var binding: SignupNicknameBinding // View Binding 객체 선언
    private lateinit var toolbar: Toolbar
    private lateinit var buttonCheckNickName: Button
    private lateinit var editTextNickName: EditText
    private lateinit var editTextPhoneNumber: EditText// Define editTextId here
    private lateinit var editTextBirthdate: EditText
    private lateinit var editTextName: EditText
    private lateinit var textViewCheckNickname: TextView // Initialize the TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding 초기화
        binding = SignupNicknameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize toolbar
        toolbar = binding.toolbarSignUpNickName
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Enable back button
        supportActionBar?.title = "회원가입" // Set the desired title


        textViewCheckNickname = binding.textViewCheckNickname
        textViewCheckNickname.visibility = View.INVISIBLE // Set initial visibility
        editTextName = binding.editTextName
        editTextNickName = binding.editTextNickName
        editTextPhoneNumber = binding.editTextPhoneNumber
        editTextBirthdate = binding.editTextBirthdate
        buttonCheckNickName = binding.buttonCheckNickName


        // 중복확인 버튼

        buttonCheckNickName.setOnClickListener {
            val userIdToCheck = editTextNickName.text.toString()
            checkNickname(userIdToCheck)
        }

        // 중복확인 후

        binding.buttonSignUpComplete.setOnClickListener {
            val receivedIntent = intent
            val usr_id = receivedIntent.getStringExtra("usr_id").toString()
            val usr_pwd = receivedIntent.getStringExtra("usr_pwd").toString()
            val name = editTextName.text.toString()
            val nickname = editTextNickName.text.toString()
            val phoneNumber = editTextPhoneNumber.text.toString()
            val gender = if (binding.man.isChecked) "남성" else "여성"
            val birth = editTextBirthdate.text.toString()

            // Call the API to save user information
            val request = SaveUserInfoRequest(usr_id, usr_pwd, name, nickname, phoneNumber, gender, birth)
            saveInfo(request)
        }
    }

    private fun saveInfo(request: SaveUserInfoRequest) {
        val service = RetrofitInstance.retrofit.create(ApiService::class.java)
        service.saveUserInfo(request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val saveInfoResponse = response.body()
                if (response.isSuccessful) {
                    // Handle a successful response from the server
                    // You can show a success message or navigate to the next screen
                    val intent = Intent(this@SignUpNicknameActivity, Fragment_Home::class.java)
                    startActivity(intent)
                } else {
                    val alertDialogBuilder = AlertDialog.Builder(this@SignUpNicknameActivity)
                    Log.d("Mp", "${saveInfoResponse?.message}")
                    alertDialogBuilder.setTitle("입력되지 않은 개인정보가 있습니다.")
                    alertDialogBuilder.setMessage("개인정보 입력 후 다시 눌러주세요.")
                    alertDialogBuilder.create().show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle network request failure
                Log.e("NetworkError", "Network request failed: ${t.message}", t)
            }
        })
    }

    private fun checkNickname(nickname: String) {
        val request = CheckNicknameRequest(nickname)
        val service = RetrofitInstance.retrofit.create(ApiService::class.java)
        // Make a network call using the service
        service.checkNickname(request).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                val checkNicknameResponse = response.body()
                if (response.isSuccessful) {
                    // 서버 응답이 200일 때
                    textViewCheckNickname.visibility = View.VISIBLE // 보이기
                    textViewCheckNickname.text = "사용가능한 닉네임입니다."
                } else {
                    // 서버 응답이 400일 때
                    textViewCheckNickname.visibility = View.VISIBLE // 보이기
                    textViewCheckNickname.text = "중복된 닉네임입니다." // 텍스트 설정
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // 네트워크 요청 실패 처리
                Log.e("NetworkError", "Network request failed: ${t.message}", t)
            }
        })
    }
}
