package com.example.appmarkettestcoding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmarkettestcoding.databinding.SampleRecyclerviewBinding
import java.text.DecimalFormat

class MyAdapter(private val mItems: MutableList<MyItem>) : RecyclerView.Adapter<MyAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    interface ItemLongClick {
        fun onLongClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null
    var itemLongClick : ItemLongClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SampleRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.itemView.setOnLongClickListener() OnLongClickListener@{
            itemLongClick?.onLongClick(it, position)
            return@OnLongClickListener true
        }

        holder.itemImageView.setImageResource(mItems[position].Image)
        holder.tvItemTitle.text = mItems[position].ItemTitle
        holder.tvAddress.text = mItems[position].Address

        val price = mItems[position].Price
        holder.tvPrice.text = DecimalFormat("#,###").format(price)+"원"

        holder.tvItemChat.text = mItems[position].ChatCnt.toString()
        holder.tvItemLike.text = mItems[position].InterestCnt.toString()

        if(mItems[position].isLike)
            holder.ivAdapterLike.setImageResource(R.drawable.img_like2)
        else
            holder.ivAdapterLike.setImageResource(R.drawable.img_like)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(binding: SampleRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemImageView = binding.iconItem
        val tvItemTitle = binding.tvItemTitle
        val tvAddress = binding.tvAddress
        val tvPrice = binding.tvPrice
        val tvItemChat = binding.tvChatCnt
        val tvItemLike = binding.tvLikecnt
        val ivAdapterLike = binding.ivLike
    }
}