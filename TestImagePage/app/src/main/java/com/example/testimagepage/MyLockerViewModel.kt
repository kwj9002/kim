package com.example.testimagepage

import androidx.lifecycle.ViewModel

class MyLockerViewModel : ViewModel() {
    private val favoriteMap = mutableMapOf<String, Boolean>()

    fun isImageFavorite(imageId: String): Boolean {
        return favoriteMap[imageId] ?: false
    }

    fun setFavorite(imageId: String, isFavorite: Boolean) {
        favoriteMap[imageId] = isFavorite
    }
}