package com.example.testimagepage

import androidx.lifecycle.ViewModel

class MyLockerViewModel : ViewModel() {
    private val favoriteMap = mutableMapOf<String, Boolean>()
    private val likedImages = mutableListOf<KakaoImage>()

    fun isImageFavorite(imageId: String): Boolean {
        return favoriteMap[imageId] ?: false
    }

    fun setFavorite(imageId: String, isFavorite: Boolean) {
        favoriteMap[imageId] = isFavorite
    }

    fun getLikedImages(): List<KakaoImage> {
        return likedImages
    }

    fun addLikedImage(image: KakaoImage) {
        likedImages.add(image)
    }

    fun removeLikedImage(image: KakaoImage) {
        likedImages.remove(image)
    }
}