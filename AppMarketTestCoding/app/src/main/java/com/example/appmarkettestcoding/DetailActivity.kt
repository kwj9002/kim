package com.example.appmarkettestcoding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appmarkettestcoding.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var myItem: MyItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myItem = intent.getParcelableExtra("selected_item") ?: return

        binding.ivDetailImage.setImageResource(myItem.aImage)
        binding.tvDetailProName.text = myItem.aProName
        binding.tvDetailSeller.text = myItem.aSeller
        binding.tvDetailAddress.text = myItem.aAddress
        binding.tvDetailPrice.text = myItem.aPrice
        binding.tvDetailProIntro.text = myItem.aProIntro

        if (myItem.isLiked) {
            binding.ivDetailGood.setImageResource(R.drawable.im_heart)
        } else {
            binding.ivDetailGood.setImageResource(R.drawable.im_heart_empty)
        }

        binding.ivDetailGood.setOnClickListener {
            toggleLike()
        }

        binding.ivDetailBack.setOnClickListener {
            returnResultAndFinish()
        }
    }

    private fun toggleLike() {
        myItem.isLiked = !myItem.isLiked

        if (myItem.isLiked) {
            binding.ivDetailGood.setImageResource(R.drawable.im_heart)
            myItem.aGood = (myItem.aGood.toInt() + 1).toString()
        } else {
            binding.ivDetailGood.setImageResource(R.drawable.im_heart_empty)
            if (myItem.aGood.toInt() > 0) {
                myItem.aGood = (myItem.aGood.toInt() - 1).toString()
            }
        }
    }

    private fun returnResultAndFinish() {
        val returnIntent = Intent()
        returnIntent.putExtra("updated_item", myItem)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}