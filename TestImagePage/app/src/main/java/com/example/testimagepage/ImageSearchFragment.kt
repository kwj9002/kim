package com.example.testimagepage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.testimagepage.databinding.FragmentImageSearchBinding

class ImageSearchFragment : Fragment() {
    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding
    private val kaKaoApiKey = "KakaoAK 21e1f67c048db5e84dd85fe16df60f61"

    private val apiService: SimpleApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SimpleApi::class.java)
    }

    private val sharedPreferencesKey = "LIKED_ITEMS"
    private val lastSearchQueryKey = "LAST_SEARCH_QUERY"
    private val savedSearchDataKey = "SAVED_SEARCH_DATA"
    private lateinit var sharedPreferences: SharedPreferences
    private var isFragmentRecreated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            "MyPrefs",
            Context.MODE_PRIVATE
        )

        if (!isFragmentRecreated) {
            val lastSearchQuery = sharedPreferences.getString(lastSearchQueryKey, "")
            binding?.editText?.setText(lastSearchQuery)

            restoreSavedSearchData()
            isFragmentRecreated = true
        } else {
            updateFavoriteStatus()
        }

        binding?.searchButton?.setOnClickListener {
            val query = binding?.editText?.text.toString()
            if (query.isNotEmpty()) {
                fetchData(query)
                sharedPreferences.edit().putString(lastSearchQueryKey, query).apply()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchData(query: String) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding?.editText?.windowToken, 0)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.searchImage(
                    apiKey = kaKaoApiKey,
                    query = query,
                    page = 1,
                    size = 80
                )

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val itemList = response.body()?.documents?.map { it.copy() } ?: emptyList()
                        updateRecyclerView(itemList.toMutableList())

                        saveLastSearchData(itemList)
                    }
                }
            } catch (_: Exception) {

            }
        }
    }

    private fun saveLastSearchData(searchData: List<KakaoImage>) {
        val savedSearchDataJson = Gson().toJson(searchData)
        sharedPreferences.edit().putString(savedSearchDataKey, savedSearchDataJson).apply()
    }

    private fun restoreSavedSearchData() {
        val savedSearchDataJson = sharedPreferences.getString(savedSearchDataKey, null)
        val savedSearchData = Gson().fromJson<List<KakaoImage>>(
            savedSearchDataJson,
            object : TypeToken<List<KakaoImage>>() {}.type
        ) ?: emptyList()

        val likedItems = getLikedItems()

        savedSearchData.forEach { image ->
            val existingLikedItem = likedItems.find { it.imageUrl == image.imageUrl }
            image.isFavorite = existingLikedItem != null
        }

        updateRecyclerView(savedSearchData.toMutableList())
    }

    private fun updateRecyclerView(itemList: MutableList<KakaoImage>) {
        val recyclerView: RecyclerView? = binding?.imageSearchRecyclerView
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)

        val imageSearchAdapter =
            ImageSearchAdapter(
                requireContext(),
                itemList,
                getLikedItems(),
                this::onImageClicked
            )

        recyclerView?.adapter = imageSearchAdapter
    }

    private fun onImageClicked(clickedKaKaoImage: KakaoImage) {
        val likedItems = getLikedItems()

        val existingLikedItem = likedItems.find { it.imageUrl == clickedKaKaoImage.imageUrl }

        if (existingLikedItem != null) {
            showToast("좋아요 취소")

            likedItems.remove(existingLikedItem)
            deleteLikedItem(existingLikedItem)

            val imageSearchAdapter = binding?.imageSearchRecyclerView?.adapter as? ImageSearchAdapter
            val position = imageSearchAdapter?.itemList?.indexOfFirst { it.imageUrl == clickedKaKaoImage.imageUrl }

            position?.let {
                imageSearchAdapter.itemList[it] = clickedKaKaoImage.copy(isFavorite = false)
                imageSearchAdapter.notifyItemChanged(it)
            }
        } else {
            showToast("좋아요 추가")

            clickedKaKaoImage.isFavorite = true
            likedItems.add(clickedKaKaoImage)
        }

        saveLikedItems(likedItems)
    }

    private fun getLikedItems(): MutableList<KakaoImage> {
        val likedItemsJson = sharedPreferences.getString(sharedPreferencesKey, null)
        val type = object : TypeToken<List<KakaoImage>>() {}.type
        return Gson().fromJson(likedItemsJson, type) ?: mutableListOf()
    }

    private fun saveLikedItems(likedItems: List<KakaoImage>) {
        val likedItemsJson = Gson().toJson(likedItems)
        sharedPreferences.edit().putString(sharedPreferencesKey, likedItemsJson).apply()
    }

    private fun deleteLikedItem(deletedKaKaoImage: KakaoImage) {
        val likedItems = getLikedItems()
        likedItems.removeAll { it.imageUrl == deletedKaKaoImage.imageUrl }
        saveLikedItems(likedItems)

        val imageSearchAdapter = binding?.imageSearchRecyclerView?.adapter as? ImageSearchAdapter
        val position = imageSearchAdapter?.itemList?.indexOfFirst { it.imageUrl == deletedKaKaoImage.imageUrl }

        position?.let {
            imageSearchAdapter.itemList[it] = deletedKaKaoImage.copy(isFavorite = false)
            imageSearchAdapter.notifyItemChanged(it)
        }
        imageSearchAdapter?.notifyDataSetChanged()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateFavoriteStatus() {
        val likedItems = getLikedItems()
        val imageSearchAdapter = binding?.imageSearchRecyclerView?.adapter as? ImageSearchAdapter

        imageSearchAdapter?.itemList?.forEach { image ->
            val existingLikedItem = likedItems.find { it.imageUrl == image.imageUrl }
            image.isFavorite = existingLikedItem != null
        }

        imageSearchAdapter?.notifyDataSetChanged()
    }
}