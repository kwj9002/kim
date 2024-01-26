package com.example.testimagepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _likedData = MutableLiveData<Boolean>()
    val likedData: LiveData<Boolean> get() = _likedData

    fun setLikedData(liked: Boolean) {
        _likedData.value = liked
    }
}