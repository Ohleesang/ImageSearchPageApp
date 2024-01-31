package com.example.imagesearchpageapp.ui.search

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchpageapp.OnClickItem
import com.example.imagesearchpageapp.ResultAdapter
import com.example.imagesearchpageapp.data.Item
import com.example.imagesearchpageapp.databinding.FragmentSearchBinding
import com.example.imagesearchpageapp.ui.mypage.MyPageViewModel
import com.google.android.material.snackbar.Snackbar

class SearchFragment : Fragment(), OnClickItem {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    private val searchViewModel: SearchViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private val resultAdapter by lazy { ResultAdapter() }
    private var checkFloating = false
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

        //앱 실행시 searchView 포커스 보여 주기
        binding.svSearchImg.apply {
            onActionViewExpanded() // SearchView를 확장
            clearFocus() // 포커스 를 제거
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setObserver()
        searchQueryEvent()
        setScrollEvent()
        setClickEvent()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     *  데이터가 변경되면 UI 변경 처리 를 위해 옵저버 설정
     */
    private fun setObserver() {

        with(searchViewModel) {
            //검색 되어 졌을때 결과를 보여 줘야 한다
            itemList.observe(viewLifecycleOwner) { newData ->
                // 이때 newData 랑 oldData를 비교하여 만약 isLike가 다르면 처리해야함
                val likeList = myPageViewModel.likeList
                val resultData = searchViewModel.checkLikeItemList(newData, likeList)

                resultAdapter.submitList(resultData)
            }

            //저장된 쿼리값 이 변경 되면 해당 쿼리값 을 저장
            savedQuery.observe(viewLifecycleOwner) { savedQuery ->
                binding.svSearchImg.setQuery(savedQuery, false)
            }


            //스크롤 위치에 따른 이벤트 처리
            scrolledY.observe(viewLifecycleOwner) { y ->
                //최상단 일때 플로팅 버튼 가리기
                if (y == 0) {
                    binding.btnFloating.apply {
                        visibility = View.GONE
                        applyFadeAnimation(this, true)
                        if (checkFloating) checkFloating = false
                    }
                } else {
                    binding.btnFloating.apply {
                        visibility = View.VISIBLE
                        if (!checkFloating) {
                            checkFloating = true
                            applyFadeAnimation(this, false)
                        }
                    }

                }
            }

            //스낵바 표시
            snackBarStr.observe(viewLifecycleOwner){str ->
                onSnackBar(str)
            }

        }
    }

    /**
     *  검색 실행 부분
     */
    private fun searchQueryEvent() {


        binding.svSearchImg.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchViewModel.fetchSearchImage(query)
                    searchViewModel.setSavedQuery(query)
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
            searchViewModel.setSavedQuery(query)
            hideKeyboard()

        }

    }

    //키보드 내리기
    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    /**
     *  플로팅 버튼 및 스크롤 이벤트
     */
    private fun setScrollEvent() {


        //최 상단에 올리기
        binding.btnFloating.setOnClickListener {
            binding.rvSearch.smoothScrollToPosition(0)
        }

        // 스크롤 될때 해당 포지션을 전달
        binding.rvSearch.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    recyclerView?.apply {
                        val totalScrollOffset = computeVerticalScrollOffset()
                        val totalScrollRange = computeVerticalScrollRange()
                        val isAtBottom = totalScrollOffset >= totalScrollRange - height

                        // y값을 전달해 줌
                        searchViewModel.checkScrollY(totalScrollOffset)

                        // 맨 아래에 위치 했을때, 추가 검색 실행 -> Animation 실행 이후 추가검색
                        if (isAtBottom) {
                            binding.lavAddSearch.apply {
                                visibility = View.VISIBLE
                                playAnimation()
                            }
                        }

                    }
                }
            })
        }
        binding.lavAddSearch.apply{
            val view = this
            addAnimatorListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator) {
                    applyFadeAnimation(view, false)
                }
                override fun onAnimationEnd(animation: Animator) {
                    //애니메이션이 끝나면 추가 검색 실행
                    visibility = View.INVISIBLE
                    val query = binding.svSearchImg.query.toString()
                    searchViewModel.scrolledOverSearch(query)
                    applyFadeAnimation(view, true)
                }
                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }

            })
        }
    }

    /**
     *  View 클릭 이벤트 처리
     */
    private fun setClickEvent() {
        resultAdapter.setOnClickedItem(this)
    }

    override fun onClick(item: Item) {
        if (item.isLike) {
            myPageViewModel.removeLikeList(item)
        } else {
            myPageViewModel.addLikeList(item)
        }
    }

    /** 위젯 페이드 인/페이드 아웃 Animation
     *
     */
    private fun applyFadeAnimation(view: View, isFadeOut: Boolean) {

        val fade: AlphaAnimation = if (isFadeOut) {
            AlphaAnimation(1.0f, 0.0f)
        } else AlphaAnimation(0.0f, 1.0f)

        //fade Out
        fade.duration = 200 // 페이드 아웃 지속 시간 단위 ms 1000 = 1초
        fade.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                if (isFadeOut) view.visibility = View.INVISIBLE // 페이드 아웃 후 뷰를 숨김
                else view.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })


        view.startAnimation(fade)

    }

    private fun onSnackBar(str :String){
        val snackBar = Snackbar.make(binding.root, str, Snackbar.LENGTH_LONG)
        val params = snackBar.view.layoutParams as FrameLayout.LayoutParams

        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.setMargins(60, binding.rvSearch.bottom-120, 60, 0)
        snackBar.view.layoutParams = params

        snackBar.show()


    }
}