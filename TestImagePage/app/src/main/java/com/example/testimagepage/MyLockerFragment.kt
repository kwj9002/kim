package com.example.testimagepage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testimagepage.databinding.FragmentMyLockerBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyLockerFragment : Fragment() {
    private var _binding: FragmentMyLockerBinding? = null
    private val binding get() = _binding!!

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


        myAdapter = MyAdapter(requireContext(), likedImages.toMutableList()) { kakaoImage ->
        }
        recyclerView.adapter = myAdapter

        return rootView
    }
}