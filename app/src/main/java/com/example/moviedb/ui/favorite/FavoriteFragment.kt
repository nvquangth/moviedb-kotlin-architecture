package com.example.moviedb.ui.favorite

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentFavoriteBinding
import com.example.moviedb.ui.detail.DetailFragment
import com.example.moviedb.ui.nowplaying.MovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    override val viewModel: FavoriteViewModel by viewModel()

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

        recycler_favorite.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            this.adapter = adapter
        }

        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            adapter.submitList(movies.toMutableList())
        })
        viewModel.getMovies()
    }

    override fun getLayoutResource(): Int = R.layout.fragment_favorite
}