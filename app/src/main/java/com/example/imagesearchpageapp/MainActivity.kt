package com.example.imagesearchpageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.imagesearchpageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initFragment()

        setDummyData()
    }

    private fun initFragment() {

        // 초기 설정
        setFragment(SearchFragment())
        //버튼 클릭시 이동
        binding.apply {
            btnFragmentSearch.setOnClickListener {
                setFragment(SearchFragment())
            }
            btnFragmentMyPage.setOnClickListener {
                setFragment(MyPageFragment())
            }
        }

    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.flMain.id, frag)
            .setReorderingAllowed(true)
            .addToBackStack("")
            .commit()
    }

    private fun setDummyData() {
        for (i in 0..10) List.mItems.add(
            Item(R.drawable.img_dummy_data, "Naver $i", "201401240422", false)
        )
    }
}