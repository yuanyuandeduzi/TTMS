package com.example.ttms.ui.mainactivitypackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.ttms.R
import com.example.ttms.databinding.ActivityMainBinding
import com.example.ttms.fragment.MineFragment
import com.example.ttms.fragment.PlanFragment
import com.example.ttms.fragment.PlayFragment
import com.example.ttms.fragment.TheatreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binging : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binging.root)
        replaceFragment(TheatreFragment())

        binging.mainNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_1 -> replaceFragment(TheatreFragment())
                R.id.tab_2 -> replaceFragment(PlayFragment())
                R.id.tab_3 -> replaceFragment(PlanFragment())
                //R.id.tab_4 -> replaceFragment(MineFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.layout_main,fragment).commit()
    }
}