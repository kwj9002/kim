package com.example.myself_introductoryapp

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val name = findViewById<EditText>(R.id.sign_Name)
        val editId = findViewById<EditText>(R.id.sign_Id)
        val editPw = findViewById<EditText>(R.id.sign_Pw)
        val join = findViewById<Button>(R.id.btn_join_out)

        // 회원가입
        join.setOnClickListener {
            val inputName = name.text.toString()
            val inputId = editId.text.toString()
            val inputPw = editPw.text.toString()
            if (inputName.isEmpty() || inputId.isEmpty() || inputPw.isEmpty()) {
                Toast.makeText(this, "입력되진 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this,SignInActivity::class.java)
                intent.putExtra("id", inputId)
                intent.putExtra("password", inputPw)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}