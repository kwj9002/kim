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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testimagepage.databinding.FragmentMyLockerBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyLockerFragment : Fragment() {
    private var _binding: FragmentMyLockerBinding? = null
    private val binding get() = _binding
    private lateinit var recyclerView: RecyclerView
    private lateinit var myLockerAdapter: MyLockerAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPreferencesKey = "LIKED_IMAGES"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLockerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding?.MyLockerRecyclerView ?: RecyclerView(requireContext())
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

        myLockerAdapter = MyLockerAdapter(requireContext(), likedImages.toMutableList()) { clickedImage ->
            showToast("이미지 클릭: ${clickedImage.imageUrl}")
        }

        recyclerView.adapter = myLockerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        recyclerView.adapter = null
    }

    fun onImagesReceived(images: List<KakaoImage>) {
        Log.d("MyLockerFragment", "onImagesReceived: ${images.size} 개의 이미지 받음")

        activity?.runOnUiThread {
            myLockerAdapter.updateData(images)
            Log.d("MyLockerFragment", "onImagesReceived: RecyclerView 갱신 완료")
        }

        if (images.isNotEmpty()) {
            for (receivedImage in images) {
                Log.d("MyLockerFragment", "이미지를 받았습니다. URL: ${receivedImage.imageUrl}")
            }
        } else {
            Log.w("MyLockerFragment", "onImagesReceived: 0개 또는 빈 이미지 목록을 받음.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}