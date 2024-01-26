package com.example.testimagepage

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testimagepage.databinding.FragmentImageSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageSearchFragment : Fragment() {

    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ImageSearchViewModel
    private val KAKAO_API_KEY = "KakaoAK 21e1f67c048db5e84dd85fe16df60f61"

    private val TAG = ImageSearchFragment::class.java.simpleName

    private val apiService: SimpleApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SimpleApi::class.java)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String? = null, param2: String? = null) =
            ImageSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel = ViewModelProvider(this).get(ImageSearchViewModel::class.java)

        val searchButton: Button = binding.searchButton
        searchButton.setOnClickListener {
            val query = binding.editText.text.toString()
            if (query.isNotEmpty()) {
                fetchData(query)
            } else {
                showToast("검색어를 입력해주세요.")
            }
        }

        viewModel.itemList?.let { itemList ->
            updateRecyclerView(itemList)
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

        Log.d(TAG, "Fetching data for query: $query with Kakao API key: $KAKAO_API_KEY")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.searchImage(
                    apiKey = KAKAO_API_KEY,
                    query = query,
                    page = 1,
                    size = 80
                )

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val itemList = response.body()?.documents ?: emptyList()
                        Log.d(TAG, "API response success. Item count: ${itemList.size}")

                        viewModel.itemList = itemList.toMutableList()

                        updateRecyclerView(itemList.toMutableList())
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(
                            TAG,
                            "API response unsuccessful. Code: ${response.code()}, Error: $errorBody"
                        )
                        showToast("검색에 실패했습니다.")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception during API call: ${e.message}", e)
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

        recyclerView.adapter = MyAdapter(requireContext(), itemList) { clickedKakaoImage ->
            onLikeButtonClicked(clickedKakaoImage)
        }
    }

    private fun onLikeButtonClicked(clickedKakaoImage: KakaoImage) {
        showToast("좋아요 버튼이 눌렸습니다.")
        Log.d(TAG, "Liked Image: ${clickedKakaoImage.imageUrl}")
    }
}