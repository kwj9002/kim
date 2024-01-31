package com.example.testimagepage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
            likedImages.toMutableList()
        ) { clickedImage ->
            showDeleteDialog(clickedImage)
        }

        recyclerView.adapter = myLockerAdapter
    }

    private fun showDeleteDialog(deletedImage: KakaoImage) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("삭제 확인")
            .setMessage("이 이미지를 정말로 삭제하시겠습니까?")
            .setPositiveButton("예") { dialogInterface: DialogInterface, _: Int ->
                onImageDeleted(deletedImage)
                dialogInterface.dismiss()
            }
            .setNegativeButton("아니오") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun onImageDeleted(deletedImage: KakaoImage) {
        myLockerAdapter.removeImage(deletedImage)

        val updatedLikedItemsJson = Gson().toJson(myLockerAdapter.getImages())
        sharedPreferences.edit().putString(sharedPreferencesKey, updatedLikedItemsJson).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        recyclerView.adapter = null
    }
}