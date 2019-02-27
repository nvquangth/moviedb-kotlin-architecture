package com.example.moviedb.ui.main

import com.example.moviedb.R
import com.example.moviedb.base.BaseActivity
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.ui.home.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun initComponentOnCreate(viewBinding: ActivityMainBinding) {
        addFragment(HomeFragment.newInstance(), R.id.container, false, HomeFragment.javaClass.simpleName)
    }

    override fun getLayoutResource(): Int = R.layout.activity_main
}
