package com.example.imagesearchpageapp


import android.os.Build
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.imagesearchpageapp.data.AppData
import com.example.imagesearchpageapp.data.SearchRepository
import com.example.imagesearchpageapp.databinding.ActivityMainBinding
import com.example.imagesearchpageapp.ui.mypage.MyPageViewModel
import com.example.imagesearchpageapp.ui.search.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val appData by lazy { AppData(application) }

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var myPageViewModel: MyPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //상태바 투명 하게 하기
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        initViewModel()
        initView()
        initSaveData()
    }

    /**
     *  뷰 초기화
     */
    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //BottomNavigation 설정
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)
    }

    /**
     *  뷰모델 및 팩토리 초기화
     */
    private fun initViewModel() {
        //뷰모델 선언
        val factory = ViewModelFactory(SearchRepository(appData))
        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]
        myPageViewModel = ViewModelProvider(this, factory)[MyPageViewModel::class.java]

    }

    /**
     *  저장된 값들 초기화
     */
    private fun initSaveData(){
        //해당 저장된 값들 불러오기
        searchViewModel.initSavedQuery()
        myPageViewModel.initLikeList()
    }

}