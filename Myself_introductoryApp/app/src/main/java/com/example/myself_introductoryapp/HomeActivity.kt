package com.example.myself_introductoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Locale
import java.util.Random

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val imageView = findViewById<ImageView>(R.id.iv_Spartan)
        when (Random().nextInt(5)) {
            0 -> imageView.setImageResource(R.drawable.ic_spartan)
            1 -> imageView.setImageResource(R.drawable.ic_spartan2)
            2 -> imageView.setImageResource(R.drawable.ic_spartan3)
            3 -> imageView.setImageResource(R.drawable.ic_spartan4)
            4 -> imageView.setImageResource(R.drawable.ic_spartan5)
        }

        val inputId = intent.getStringExtra("id")
        val idText = findViewById<TextView>(R.id.tv_Id)
        idText.text = inputId

        val finish = findViewById<Button>(R.id.btn_close)
        finish.setOnClickListener {
            finish()
        }

    }
}