package com.example.imagesearchpageapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.imagesearchpageapp.data.SearchRepository
import com.example.imagesearchpageapp.databinding.ActivityMainBinding
import com.example.imagesearchpageapp.ui.mypage.MyPageViewModel
import com.example.imagesearchpageapp.ui.search.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰모델 선언
        val factory = ViewModelFactory(SearchRepository(application))
        val searchViewModel = ViewModelProvider(this,factory)[SearchViewModel::class.java]
        val myPageViewModel = ViewModelProvider(this,factory)[MyPageViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)




    }
}