package com.example.testimagepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class MyLockerAdapter(
    private val context: Context,
    private var itemList: MutableList<KakaoImage>,
    private val onImageClickListener: (KakaoImage) -> Unit
) : RecyclerView.Adapter<MyLockerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageview)
        val textSiteName: TextView = view.findViewById(R.id.display_sitename)
        val textTime: TextView = view.findViewById(R.id.datetime)
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

        holder.itemView.setOnClickListener {
            onImageClickListener.invoke(kakaoImage)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateData(images: List<KakaoImage>) {
        itemList.clear()
        itemList.addAll(images)
        notifyDataSetChanged()
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        Picasso.get().load(url).into(imageView)
    }
}