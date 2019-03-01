package com.example.moviedb.ui.detail

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    companion object {
        const val TAG = "DetailFragment"
        const val ARGUMENT_MOVIE = "ARGUMENT_MOVIE"
        fun newInstance(movie: Movie): DetailFragment {
            val fragment = DetailFragment()
            val bundle: Bundle = Bundle()
            bundle.putParcelable(ARGUMENT_MOVIE, movie)
            fragment.arguments = bundle
            return fragment
        }
    }

    override val viewModel: DetailViewModel by viewModel()

    override fun initComponentOnActivityCreated(viewBinding: ViewDataBinding) {

    }

    override fun getLayoutResource(): Int = R.layout.fragment_detail
}