package com.example.moviedb.ui.home

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentHomeBinding
import com.example.moviedb.ui.favorite.FavoriteFragment
import com.example.moviedb.ui.nowplaying.NowPlayingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    companion object {
        const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }

    override val viewModel: HomeViewModel by viewModel()

    override fun initComponentOnActivityCreated(
        viewBinding: ViewDataBinding,
        savedInstanceState: Bundle?
    ) {
        if (savedInstanceState == null) {
            replaceNowPlayingFragment()
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun getLayoutResource() = R.layout.fragment_home

    private fun replaceNowPlayingFragment() {
        var fragment = findFragmentByTag(NowPlayingFragment.TAG)
        if (fragment == null) {
            fragment = NowPlayingFragment.newInstance()
        }
        replaceFragment(
            fragment,
            R.id.container,
            true,
            NowPlayingFragment.TAG
        )
    }

    private fun replaceFavoriteFragment() {
        var fragment = findFragmentByTag(FavoriteFragment.TAG)
        if (fragment == null) {
            fragment = FavoriteFragment.newInstance()
        }
        replaceFragment(
            fragment,
            R.id.container,
            true,
            FavoriteFragment.TAG
        )
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceNowPlayingFragment()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    replaceFavoriteFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}