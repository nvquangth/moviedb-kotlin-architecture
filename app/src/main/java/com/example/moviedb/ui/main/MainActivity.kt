package com.example.moviedb.ui.main

import androidx.lifecycle.Observer
import com.example.moviedb.R
import com.example.moviedb.base.BaseActivity
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.ui.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModel()

    override fun initComponentOnCreate(viewBinding: ActivityMainBinding) {
        addFragment(
            HomeFragment.newInstance(),
            R.id.container,
            false,
            HomeFragment.TAG
        )

        viewModel.getStateActionBar().observe(this, Observer { active ->
            if (active) {
                showBackActionbar()
            } else {
                hideBackActionbar()
            }
        })
    }

    override fun getLayoutResource(): Int = R.layout.activity_main
}
