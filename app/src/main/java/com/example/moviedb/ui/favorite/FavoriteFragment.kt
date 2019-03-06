package com.example.moviedb.ui.favorite

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentFavoriteBinding
import com.example.moviedb.ui.detail.DetailFragment
import com.example.moviedb.ui.nowplaying.MovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment: BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    companion object {
        const val TAG = "FavoriteFragment"
        fun newInstance() = FavoriteFragment()
    }

    override val viewModel: FavoriteViewModel by viewModel()

    override fun initComponentOnActivityCreated(viewBinding: ViewDataBinding) {
        val adapter = MovieAdapter {movie ->
            addFragmentToActivity(
                DetailFragment.newInstance(movie),
                R.id.container,
                true,
                DetailFragment.TAG
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

        viewModel.movies.observe(viewLifecycleOwner, Observer {movies ->
            adapter.submitList(movies)
        })
        viewModel.getMovies()
    }

    override fun getLayoutResource(): Int = R.layout.fragment_favorite
}