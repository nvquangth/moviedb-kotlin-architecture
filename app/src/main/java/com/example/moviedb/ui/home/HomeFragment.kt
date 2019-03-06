package com.example.moviedb.ui.home

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

    override fun initComponentOnActivityCreated(viewBinding: ViewDataBinding) {
        addFragment(
            NowPlayingFragment.newInstance(),
            R.id.container,
            false,
            NowPlayingFragment.TAG
        )
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun getLayoutResource() = R.layout.fragment_home

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(
                        NowPlayingFragment.newInstance(),
                        R.id.container,
                        false,
                        NowPlayingFragment.TAG
                    )
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorite -> {
                    replaceFragment(
                        FavoriteFragment.newInstance(),
                        R.id.container,
                        false,
                        NowPlayingFragment.TAG
                    )
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}