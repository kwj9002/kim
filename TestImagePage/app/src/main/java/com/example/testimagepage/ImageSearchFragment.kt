package com.example.testimagepage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
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
    private val binding get() = _binding!!
    private val kaKaoApiKey = "KakaoAK 21e1f67c048db5e84dd85fe16df60f61"

    private val apiService: SimpleApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SimpleApi::class.java)
    }

    private val sharedPreferencesKey = "LIKED_ITEMS"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        val rootView = binding.root


        sharedPreferences = requireContext().getSharedPreferences(
            "MyPrefs",
            Context.MODE_PRIVATE
        )

        val likedItemsJson = sharedPreferences.getString(sharedPreferencesKey, null)
        Gson().fromJson<List<KakaoImage>>(
            likedItemsJson,
            object : TypeToken<List<KakaoImage>>() {}.type
        )

        val searchButton: Button = binding.searchButton
        searchButton.setOnClickListener {
            val query = binding.editText.text.toString()
            if (query.isNotEmpty()) {
                fetchData(query)
            } else {
                showToast("검색어를 입력해주세요.")
            }
        }


        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchData(query: String) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.editText.windowToken, 0)

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
                        val itemList = response.body()?.documents ?: emptyList()


                        updateRecyclerView(itemList.toMutableList())
                    } else {
                        showToast("검색에 실패했습니다.")
                    }
                }
            } catch (e: Exception) {
                showToast("에러가 발생했습니다.")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateRecyclerView(itemList: MutableList<KakaoImage>) {
        val recyclerView: RecyclerView = binding.imageSearchRecyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val myAdapter = MyAdapter(requireContext(), itemList) { clickedKakaoImage ->
            onLikeButtonClicked(clickedKakaoImage)
        }

        recyclerView.adapter = myAdapter
    }

    private fun onLikeButtonClicked(clickedKakaoImage: KakaoImage) {
        showToast("좋아요 버튼이 눌렸습니다.")

    }
}