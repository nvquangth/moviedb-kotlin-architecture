package com.example.moviedb.ui.nowplaying

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentNowplayingBinding
import com.example.moviedb.ui.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_nowplaying.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment : BaseFragment<FragmentNowplayingBinding, NowPlayingViewModel>() {

    var page = 1

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

        val layoutManager = LinearLayoutManager(context)

        recycler_movie.apply {
            this.layoutManager = layoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            this.adapter = adapter

        }


        recycler_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm: LinearLayoutManager = recycler_movie.layoutManager as LinearLayoutManager
                if (lm.findLastVisibleItemPosition() == lm.itemCount - 1) {
                    if (viewModel.loading.value == false) {
                        page++
                        viewModel.getMovies(page)
                    }
                }
            }
        })

        viewModel.getData().observe(viewLifecycleOwner, Observer { movies ->
            adapter.submitList(movies)
        })
        viewModel.getMovies(page)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_nowplaying
}