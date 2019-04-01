package com.example.moviedb.ui.nowplaying2

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentNowplaying2Binding
import kotlinx.android.synthetic.main.fragment_nowplaying_2.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment2: BaseFragment<FragmentNowplaying2Binding, NowPlayingViewModel2>() {

    override val viewModel: NowPlayingViewModel2 by viewModel()

    override fun initComponentOnActivityCreated(
        viewBinding: ViewDataBinding,
        savedInstanceState: Bundle?
    ) {
        val adapter = MovieAdapter2 {
            viewModel.retry()
        }

        recycler_movie.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })
    }

    override fun getLayoutResource(): Int = R.layout.fragment_nowplaying_2
}