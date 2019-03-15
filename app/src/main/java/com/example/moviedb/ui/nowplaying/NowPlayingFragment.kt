package com.example.moviedb.ui.nowplaying

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentNowplayingBinding
import com.example.moviedb.ui.EndlessScrollListener
import com.example.moviedb.ui.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_nowplaying.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment : BaseFragment<FragmentNowplayingBinding, NowPlayingViewModel>() {

    override val viewModel: NowPlayingViewModel by viewModel()

    override fun initComponentOnActivityCreated(
        viewBinding: ViewDataBinding,
        savedInstanceState: Bundle?
    ) {
        val adapter = MovieAdapter { movie ->
            findNavController().navigate(
                R.id.detail_dest,
                bundleOf(
                    DetailFragment.ARGUMENT_MOVIE to movie,
                    DetailFragment.ARGUMENT_TITLE to movie.mTitle
                )
            )
        }

        recycler_movie.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            this.adapter = adapter

        }

        if (recycler_movie.layoutManager is LinearLayoutManager) {
            val layoutManager = recycler_movie.layoutManager as LinearLayoutManager
            recycler_movie.addOnScrollListener(EndlessScrollListener(layoutManager) {
                if (viewModel.loading.value == false && viewModel.refreshData.value == false) {
                    viewModel.getMovies(viewModel.currentPage)
                }
            })
        }

        viewModel.getData().observe(viewLifecycleOwner, Observer { movies ->
            adapter.submitList(movies)
        })
        viewModel.refreshData.observe(viewLifecycleOwner, Observer { refresh ->
            swipe_refresh_layout?.let {
                swipe_refresh_layout.isRefreshing = refresh
            }
        })
        viewModel.getMovies(viewModel.currentPage)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_nowplaying
}