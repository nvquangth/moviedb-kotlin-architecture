package com.example.moviedb.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.moviedb.R
import com.example.moviedb.base.BaseActivity
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.ui.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModel()

    override fun initComponentOnCreate(viewBinding: ActivityMainBinding, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            replaceHomeFragment()
        }

        viewModel.actionBarState.observe(this, Observer { active ->
            if (active) {
                showBackActionbar()
            } else {
                hideBackActionbar()
            }
        })
    }

    override fun getLayoutResource(): Int = R.layout.activity_main

    private fun replaceHomeFragment() {
        var fragment = findFragmentByTag(HomeFragment.TAG)
        if (fragment == null) {
            fragment = HomeFragment.newInstance()
        }
        replaceFragment(
            fragment,
            R.id.container,
            false,
            HomeFragment.TAG
        )
    }
}
