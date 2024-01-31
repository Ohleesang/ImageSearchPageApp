package com.example.imagesearchpageapp.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchpageapp.OnClickItem
import com.example.imagesearchpageapp.ResultAdapter
import com.example.imagesearchpageapp.data.Item
import com.example.imagesearchpageapp.databinding.FragmentMyPageBinding
import com.example.imagesearchpageapp.ui.search.SearchViewModel

class MyPageFragment : Fragment(),OnClickItem {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel : SearchViewModel by activityViewModels()
    private val myPageViewModel : MyPageViewModel by activityViewModels()
    private val resultAdapter by lazy { ResultAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        binding.rvMyPage.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = resultAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //클릭 인터페이스 등록
        resultAdapter.setOnClickedItem(this)

        //리스트 값 변경 되면 자동으로 UI 업데이트
        myPageViewModel.likeList.observe(viewLifecycleOwner){ likeData ->
            resultAdapter.submitList(likeData?.toList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(item: Item) {
        myPageViewModel.removeLikeList(item)
        searchViewModel.disLikeItem(item)
    }
}