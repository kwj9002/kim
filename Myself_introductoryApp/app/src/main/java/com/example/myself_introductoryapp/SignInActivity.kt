package com.example.myself_introductoryapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setResultSignUp()

        val login = findViewById<Button>(R.id.btn_login)
        val join = findViewById<Button>(R.id.btn_join)
        val editId = findViewById<EditText>(R.id.edit_Id)
        val editPw = findViewById<EditText>(R.id.edit_Pw)

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("name", "value")
        intent.putExtra("id", "value")


        login.setOnClickListener {
            val inputId = editId.text.toString()
            val inputPw = editPw.text.toString()
            if (inputId.isEmpty() || inputPw.isEmpty()) {
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("id", inputId)
                startActivity(intent)
            }
        }

        join.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resurtLauncher.launch(intent)
        }
    }

    private lateinit var resurtLauncher: ActivityResultLauncher<Intent>

    private fun setResultSignUp() {
        resurtLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val inputId = data?.getStringExtra("id")
                val inputPw = data?.getStringExtra("password")
                val getId = findViewById<EditText>(R.id.edit_Id)
                getId.setText(inputId)
                val getPw = findViewById<EditText>(R.id.edit_Pw)
                getPw.setText(inputPw)
            }
        }
    }
}