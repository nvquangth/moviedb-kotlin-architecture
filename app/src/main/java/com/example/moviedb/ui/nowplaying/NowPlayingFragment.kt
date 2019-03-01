package com.example.moviedb.ui.nowplaying

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentNowplayingBinding
import com.example.moviedb.ui.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_nowplaying.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment : BaseFragment<FragmentNowplayingBinding, NowPlayingViewModel>() {

    companion object {
        const val TAG = "NowPlayingFragment"
        fun newInstance() = NowPlayingFragment()
    }

    override val viewModel: NowPlayingViewModel by viewModel()

    override fun initComponentOnActivityCreated(viewBinding: ViewDataBinding) {
        val adapter = MovieAdapter { movie ->
            addFragmentToActivity(
                DetailFragment.newInstance(movie),
                R.id.container,
                true,
                DetailFragment.TAG
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

        viewModel.getData().observe(this, Observer { movies ->
            adapter.submitList(movies)
        })
        viewModel.getMovies(1)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_nowplaying
}