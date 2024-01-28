package com.example.imagesearchpageapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchpageapp.ResultAdapter
import com.example.imagesearchpageapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    private val searchViewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }
    private val resultAdapter by lazy { ResultAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = resultAdapter
        }

        //기존에 저장된 검색어 값 불러오기
        searchViewModel.initSavedQuery(requireContext())

        //앱 실행시 저장된 쿼리값 보여주기
        binding.svSearchImg.apply {
            onActionViewExpanded() // SearchView를 확장
            clearFocus() // 포커스를 제거
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchViewModel.itemList.observe(viewLifecycleOwner) { newData ->
            resultAdapter.submitList(newData.toList())
        }

        searchViewModel.savedQuery.observe(viewLifecycleOwner) { savedQuery ->
            binding.svSearchImg.setQuery(savedQuery, false)
        }


        binding.svSearchImg.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchViewModel.fetchSearchImage(query)
                    searchViewModel.setSavedQuery(requireContext(), query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        }

        binding.btnSearchOk.setOnClickListener {
            val query = binding.svSearchImg.query.toString()
            searchViewModel.fetchSearchImage(query)
            searchViewModel.setSavedQuery(requireContext(), query)
            hideKeyboard()

        }
    }


    //키보드 내리기
    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}