package com.example.testimagepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testimagepage.databinding.FragmentMyLockerBinding

const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"

class MyLockerFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentMyLockerBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var myLockerViewModel: MyLockerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyLockerBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // ViewModel 초기화
        myLockerViewModel = ViewModelProvider(this).get(MyLockerViewModel::class.java)

        recyclerView = binding.recyclerViewMyLocker
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter 초기화
        val itemList = mutableListOf<KakaoImage>()
        myAdapter = MyAdapter(requireContext(), itemList) { kakaoImage ->
            val isFavorite = myLockerViewModel.isImageFavorite(kakaoImage.imageUrl)
            myLockerViewModel.setFavorite(kakaoImage.imageUrl, !isFavorite)
            myAdapter.notifyDataSetChanged()
        }
        recyclerView.adapter = myAdapter

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = MyLockerFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}