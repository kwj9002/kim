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
    private val binding get() = _binding
    private lateinit var recyclerView: RecyclerView
    private lateinit var myLockerAdapter: MyLockerAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPreferencesKey = "LIKED_ITEMS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLockerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding?.myLockerRecyclerView ?: RecyclerView(requireContext())
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

        myLockerAdapter = MyLockerAdapter(
            requireContext(),
            likedImages.toMutableList(),
            onImageDeleteListener = { deletedImage ->
                onImageDeleted(deletedImage)
            }
        )

        recyclerView.adapter = myLockerAdapter
    }

    private fun onImageDeleted(deletedImage: KakaoImage) {
        myLockerAdapter.removeImage(deletedImage)

        val updatedLikedItemsJson = Gson().toJson(myLockerAdapter.getImages())
        sharedPreferences.edit().putString(sharedPreferencesKey, updatedLikedItemsJson).apply()
    }
}