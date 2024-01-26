package com.example.testimagepage

import androidx.lifecycle.ViewModel

class MyLockerViewModel : ViewModel() {
    private val favoriteMap = mutableMapOf<String, Boolean>()
    private val likedImages: MutableList<KakaoImage> = mutableListOf()

    fun isImageFavorite(imageId: String): Boolean {
        return favoriteMap[imageId] ?: false
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

    fun setLikedImages(likedImages: List<KakaoImage>) {
        this.likedImages.clear()
        this.likedImages.addAll(likedImages)
    }
}