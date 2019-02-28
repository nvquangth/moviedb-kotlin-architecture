package com.example.moviedb.ui.nowplaying

import androidx.databinding.ViewDataBinding
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentNowplayingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment : BaseFragment<FragmentNowplayingBinding, NowPlayingViewModel>() {

    companion object {
        fun newInstance() = NowPlayingFragment()
    }

    override val viewModel: NowPlayingViewModel by viewModel()

    override fun initComponentOnActivityCreated(viewBinding: ViewDataBinding) {
    }

    override fun getLayoutResource(): Int = R.layout.fragment_nowplaying
}