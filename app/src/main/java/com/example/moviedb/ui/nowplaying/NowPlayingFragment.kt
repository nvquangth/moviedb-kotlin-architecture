package com.example.moviedb.ui.nowplaying

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentNowplayingBinding
import com.example.moviedb.ui.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_nowplaying.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment : BaseFragment<FragmentNowplayingBinding, NowPlayingViewModel>() {

    override val viewModel: NowPlayingViewModel by viewModel()

    override fun initComponentOnActivityCreated(
        viewBinding: ViewDataBinding,
        savedInstanceState: Bundle?
    ) {
        val adapter = MoviePageListAdapter({ movie ->
            findNavController().navigate(
                R.id.detail_dest,
                bundleOf(
                    DetailFragment.ARGUMENT_MOVIE to movie,
                    DetailFragment.ARGUMENT_TITLE to movie.mTitle
                )
            )
        }, {
            viewModel.retry()
        })

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

    override fun getLayoutResource(): Int = R.layout.fragment_nowplaying
}