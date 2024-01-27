package com.example.testimagepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(
    private val context: Context,
    private var itemList: MutableList<KakaoImage>,
    private val onLikeClickListener: (KakaoImage) -> Unit
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageview)
        val textSiteName: TextView = view.findViewById(R.id.display_sitename)
        val textTime: TextView = view.findViewById(R.id.datetime)
        val favoriteImageView: ImageView = view.findViewById(R.id.favoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kakaoImage = itemList[position]

        loadImageFromUrl(kakaoImage.imageUrl, holder.imageView)

        holder.textSiteName.text = kakaoImage.siteName
        holder.textTime.text = kakaoImage.datetime

        updateFavoriteImage(holder.favoriteImageView, kakaoImage.isFavorite)

        holder.favoriteImageView.setOnClickListener {
            kakaoImage.isFavorite = !kakaoImage.isFavorite
            onLikeClickListener.invoke(kakaoImage)

            updateFavoriteImage(holder.favoriteImageView, kakaoImage.isFavorite)

            showToast(if (kakaoImage.isFavorite) "이미지를 즐겨찾기에 추가했습니다." else "이미지를 즐겨찾기에서 제거했습니다.")
        }
    }

    private fun updateFavoriteImage(imageView: ImageView, isFavorite: Boolean) {
        imageView.setImageResource(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_filled)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        Picasso.get().load(url).into(imageView)
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}