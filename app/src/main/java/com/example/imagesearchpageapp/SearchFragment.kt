package com.example.imagesearchpageapp

import android.os.Bundle
import android.content.Context
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
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

    private val _searchImages = MutableLiveData<List<Document>>()
    private var searchImages : LiveData<List<Document>> = _searchImages.switchMap {
        MutableLiveData(it)
    }
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
        binding.rvSearch.apply {
            adapter = ResultAdapter(ListItem.mCardItems)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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

            val str = binding.svSearchImg.query
            //데이터 전달

            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    //데이터 요청 / 받기
    private fun fetchSearchImages(query:String){
        CoroutineScope(Dispatchers.Main).launch{
            val images = searchImages(query)
            _searchImages.value = images
        }
    }
    private suspend fun searchImages(query: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.searchImages(query = query).documents
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}