package com.example.testimagepage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    }

    fun saveLikedImages(likedImages: Set<KakaoImage>) {
        val editor = sharedPreferences.edit()
        val jsonString = Gson().toJson(likedImages)
        editor.putString("likedImages", jsonString)
        editor.apply()
    }

    fun getLikedImages(): Set<KakaoImage>? {
        val jsonString = sharedPreferences.getString("likedImages", "")
        return if (jsonString.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(jsonString, object : TypeToken<Set<KakaoImage>>() {}.type)
        }
    }
}