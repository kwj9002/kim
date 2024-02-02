package com.example.testimagepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.reflect.KFunction1

class ImageSearchAdapter(
    private val context: Context,
    val itemList: MutableList<KakaoImage>,
    private val likedItems: MutableList<KakaoImage>,
    private val onImageClickListener: (KakaoImage) -> Unit
) : RecyclerView.Adapter<ImageSearchAdapter.ViewHolder>() {

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

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = dateFormat.format(kakaoImage.datetime)
        holder.textTime.text = date

        val isLiked = likedItems.any { it.imageUrl == kakaoImage.imageUrl }
        holder.favoriteImageView.visibility = if (isLiked) View.VISIBLE else View.GONE

        holder.imageView.setOnClickListener {
            kakaoImage.isFavorite = !isLiked

            if (kakaoImage.isFavorite) {
                likedItems.add(kakaoImage)
            } else {
                likedItems.remove(kakaoImage)
            }

            val imageResource = if (kakaoImage.isFavorite) R.drawable.ic_favorite else 0
            holder.favoriteImageView.setImageResource(imageResource)

            holder.favoriteImageView.visibility =
                if (kakaoImage.isFavorite) View.VISIBLE else View.GONE

            notifyItemChanged(position)

            onImageClickListener.invoke(kakaoImage)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        Picasso.get()
            .load(url)
            .into(imageView)
    }
}