package com.example.testimagepage

import androidx.lifecycle.ViewModel

class ImageSearchViewModel : ViewModel() {
    var itemList: MutableList<KakaoImage> = mutableListOf()

    fun clearData() {
        itemList.clear()
    }
}