package com.example.testimagepage

import android.content.SharedPreferences
import android.os.Bundle
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

        myAdapter = MyAdapter(requireContext(), myLockerViewModel.getLikedImages()) { kakaoImage ->
            myLockerViewModel.removeLikedImage(kakaoImage)
            myAdapter.notifyDataSetChanged()
            saveLikedImagesToSharedPreferences()
            showToast("이미지를 즐겨찾기에서 제거했습니다.")
        }
        recyclerView.adapter = myAdapter

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveLikedImagesToSharedPreferences() {
        val likedImages = myLockerViewModel.getLikedImages()
        val likedImagesJson = Gson().toJson(likedImages)
        sharedPreferences.edit().putString(sharedPreferencesKey, likedImagesJson).apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}