package com.example.testimagepage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testimagepage.databinding.FragmentMyLockerBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyLockerFragment : Fragment() {
    private var _binding: FragmentMyLockerBinding? = null
    private val binding get() = _binding!!

    private lateinit var myLockerViewModel: MyLockerViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPreferencesKey = "LIKED_IMAGES"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyLockerBinding.inflate(inflater, container, false)
        val rootView = binding.root

        myLockerViewModel = ViewModelProvider(this).get(MyLockerViewModel::class.java)

        recyclerView = binding.recyclerViewMyLocker
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        sharedPreferences = requireContext().getSharedPreferences(
            "MyPrefs",
            Context.MODE_PRIVATE
        )

        val likedImagesJson = sharedPreferences.getString(sharedPreferencesKey, null)
        val likedImages = Gson().fromJson<List<KakaoImage>>(
            likedImagesJson,
            object : TypeToken<List<KakaoImage>>() {}.type
        ) ?: emptyList()

        myLockerViewModel.setLikedImages(likedImages)

        myAdapter = MyAdapter(requireContext(), likedImages.toMutableList()) { kakaoImage ->
            onLikeButtonClicked(kakaoImage)
        }
        recyclerView.adapter = myAdapter

        return rootView
    }

    private fun onLikeButtonClicked(clickedKakaoImage: KakaoImage) {
        showToast("좋아요 버튼이 눌렸습니다.")

        if (::myLockerViewModel.isInitialized) {
            val isFavorite = myLockerViewModel.isImageFavorite(clickedKakaoImage.imageUrl)

            if (isFavorite) {
                myLockerViewModel.removeLikedImage(clickedKakaoImage)
            } else {
                myLockerViewModel.addLikedImage(clickedKakaoImage)
            }

            likedImagesToSharedPreferences()
            updateLikedImages()

            Log.d("MyLockerFragment", "Updated Liked Images: ${myLockerViewModel.getLikedImages()}")
        }
    }

    private fun likedImagesToSharedPreferences() {
        val likedImages = myLockerViewModel.getLikedImages()

        val likedImagesJson = Gson().toJson(likedImages)
        sharedPreferences.edit().putString(sharedPreferencesKey, likedImagesJson).apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateLikedImages() {
        val likedImages = myLockerViewModel.getLikedImages()

        myAdapter.updateData(likedImages.toMutableList())
    }
}