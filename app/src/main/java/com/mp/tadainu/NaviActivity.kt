package com.mp.tadainu
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mp.tadainu.Fragment_Feed
import com.mp.tadainu.Fragment_Home
import com.mp.tadainu.Fragment_Mypet
import com.mp.tadainu.Fragment_board
import com.mp.tadainu.R

class NaviActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navi)

        bottomNavigationView = findViewById(R.id.navigationView)

        // 첫 번째 Fragment를 화면에 표시
        val initialFragment = Fragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, initialFragment)
            .commit()

        // BottomNavigationView에서 항목을 선택할 때 동작 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
//                R.id.homeFragment -> {
//                    val homeFragment = Fragment_Home()
//                    replaceFragment(homeFragment)
//                    true
//                }
                R.id.feedFragment -> {
                    val feedFragment = Fragment_Feed()
                    replaceFragment(feedFragment)
                    true
                }
                R.id.boardFragment -> {
                    val boardFragment = Fragment_board()
                    replaceFragment(boardFragment)
                    true
                }
                R.id.myPetFragment -> {
                    val myPetFragment = Fragment_Mypet()
                    replaceFragment(myPetFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, fragment)
            .commit()
    }
}
