package com.example.moviedb.ui.detail

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentDetailBinding
import com.example.moviedb.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    companion object {
        const val TAG = "DetailFragment"
        private const val ARGUMENT_MOVIE = "ARGUMENT_MOVIE"
        fun newInstance(movie: Movie) = DetailFragment().apply {
            arguments = bundleOf(
                ARGUMENT_MOVIE to movie
            )
        }
    }

    override val viewModel: DetailViewModel by viewModel()

    override fun initComponentOnActivityCreated(
        viewBinding: ViewDataBinding,
        savedInstanceState: Bundle?
    ) {
        mainViewModel.actionBarState.value = true

        val movie: Movie? = arguments?.getParcelable(ARGUMENT_MOVIE)
        viewModel.movie.value = movie ?: return
        viewModel.checkFavorite()
    }

    override fun getLayoutResource(): Int = R.layout.fragment_detail

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.actionBarState.value = false
    }
}