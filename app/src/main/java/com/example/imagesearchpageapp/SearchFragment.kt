package com.example.imagesearchpageapp

import android.os.Bundle
import android.content.Context
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchpageapp.databinding.FragmentSearchBinding
import com.example.imagesearchpageapp.retrofit.RetrofitInstance
import com.example.imagesearchpageapp.retrofit.data.Document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSearchBinding

    private lateinit var resultAdapter: ResultAdapter

    private val _searchImages = MutableLiveData<List<Document>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        resultAdapter = ResultAdapter(requireContext(), ListItem.mItems)
        binding.rvSearch.apply {
            adapter = resultAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // 서버에 받아온 데이터가 변경이 일어날때 처리
        _searchImages.observe(viewLifecycleOwner) { list ->
            resultAdapter.updateUI()
        }

        // 검색 기능
        binding.svSearchImg.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //데이터 전달
                fetchSearchImages(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        binding.btnSearchOk.setOnClickListener {

            val query = binding.svSearchImg.query.toString()
            //데이터 전달
            fetchSearchImages(query)
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    //데이터 요청 / 받기
    private fun fetchSearchImages(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val documents = searchImages(query)
            val newItems = mutableListOf<Item>()
            documents.forEach {
                //이후 데이터를 저장
                newItems.add(Item(false,it.dateTime,it.siteName,it.thumbNailUrl))
            }
            ListItem.mItems = newItems
            _searchImages.value = documents
        }
    }

    private suspend fun searchImages(query: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.searchImages(query = query).documents
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}