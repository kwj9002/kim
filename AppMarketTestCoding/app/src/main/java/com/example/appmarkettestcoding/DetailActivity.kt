package com.example.appmarkettestcoding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.appmarkettestcoding.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private var isLike = false

    private val item: MyItem? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.ITEM_OBJECT,MyItem::class.java)
        } else {
            intent.getParcelableExtra<MyItem>(Constants.ITEM_OBJECT)
        }
    }

    private val itemPosition: Int by lazy {
        intent.getIntExtra(Constants.ITEM_INDEX,0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("jblee","itemPosition = $itemPosition")

        binding.ivItemImage.setImageDrawable(item?.let {
            ResourcesCompat.getDrawable(
                resources,
                it.Image,
                null
            )
        })
        binding.tvSellerName.text = item?.SellerName
        binding.tvSellerAddress.text = item?.Address
        binding.tvITemTitle.text = item?.ItemTitle
        binding.tvITemDetail.text = item?.ItemDetail
        binding.tvITemDetailPrice.text = DecimalFormat("#,###").format(item?.Price) + "원"

        isLike = item?.isLike == true

        binding.ivDetailLike.setImageResource(if (isLike) {R.drawable.img_like2}else{R.drawable.img_like})

        binding.ivBack.setOnClickListener {
            exit()
        }
        binding.llDetailLike.setOnClickListener {
            if(!isLike){
                binding.ivDetailLike.setImageResource(R.drawable.img_like2)
                Snackbar.make(binding.constLayout, "관심 목록에 추가되었습니다.", Snackbar.LENGTH_SHORT).show()
                isLike = true
            }else {
                binding.ivDetailLike.setImageResource(R.drawable.img_like)
                isLike = false
            }
        }
    }
    fun exit() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("itemIndex", itemPosition)
            putExtra("isLike", isLike)
        }
        setResult(RESULT_OK, intent)
        if (!isFinishing) finish()
    }
    override fun onBackPressed() {
        exit()
    }
}