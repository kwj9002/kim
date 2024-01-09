package com.example.appmarkettestcoding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmarkettestcoding.databinding.SampleRecyclerBinding

class MyAdapter(val mItems: MutableList<MyItem>) : RecyclerView.Adapter<MyAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    interface ItemLongClick {
        fun onLongClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    var itemLongClick: ItemLongClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            SampleRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.itemView.setOnLongClickListener {
            itemLongClick?.onLongClick(it, position)
            true
        }

        holder.iconImageView.setImageResource(mItems[position].aImage)
        holder.name.text = mItems[position].aProName
        holder.address.text = mItems[position].aAddress
        holder.price.text = mItems[position].aPrice
        holder.chat.text = mItems[position].aChatting
        holder.good.text = mItems[position].aGood

        holder.good.setOnClickListener {
            val currentLikes = mItems[position].aGood.toInt()
            val updatedLikes = currentLikes + 1
            mItems[position].aGood = updatedLikes.toString()
            holder.good.text = mItems[position].aGood // 좋아요 수 업데이트
        }
    }


        override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(val binding: SampleRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        val iconImageView = binding.iconItem
        val name = binding.textItem1
        val address = binding.textItem2
        val price = binding.textItem3
        val chat = binding.textChat
        val good = binding.textLike
    }

    fun updateLikeCount(itemPosition: Int, newLikeCount: String) {
        mItems[itemPosition].aGood = newLikeCount
        notifyDataSetChanged()
    }
}