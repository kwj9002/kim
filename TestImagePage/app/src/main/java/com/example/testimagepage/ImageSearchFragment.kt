package com.example.testimagepage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testimagepage.databinding.FragmentImageSearchBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    private lateinit var sharedPreferences: SharedPreferences

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

        val lastSearchQuery = sharedPreferences.getString(lastSearchQueryKey, "")
        binding?.editText?.setText(lastSearchQuery)

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
                    }
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun updateRecyclerView(itemList: MutableList<KakaoImage>) {
        val recyclerView: RecyclerView? = binding?.imageSearchRecyclerView
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)

        val myAdapter = MyAdapter(requireContext(), itemList) { clickedKakaoImage ->
            onLikeButtonClicked(clickedKakaoImage)
        }

        recyclerView?.adapter = myAdapter
    }

    private fun onLikeButtonClicked(clickedKaKaoImage: KakaoImage) {
        val likedItemsJson = sharedPreferences.getString(sharedPreferencesKey, null)
        val likedItems = Gson().fromJson<List<KakaoImage>>(
            likedItemsJson,
            object : TypeToken<List<KakaoImage>>() {}.type
        )?.toMutableList() ?: mutableListOf()

        val existingLikedItem = likedItems.find { it.imageUrl == clickedKaKaoImage.imageUrl }

        if (existingLikedItem != null) {
            existingLikedItem.isFavorite = !existingLikedItem.isFavorite
        } else {
            clickedKaKaoImage.isFavorite = true
            likedItems.add(clickedKaKaoImage)
        }

        val likedItemsJsonUpdated = Gson().toJson(likedItems)
        sharedPreferences.edit().putString(sharedPreferencesKey, likedItemsJsonUpdated).apply()

        (requireActivity() as? MainActivity)?.onImageSelected(likedItems.filter { it.isFavorite })

        for (likedItem in likedItems.filter { it.isFavorite }) {
            println("ImageSearchFragment: 좋아요 누른 이미지 URL: ${likedItem.imageUrl}")
        }
    }
}