package com.example.moviedb.ui.detail

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.navArgs
import com.example.moviedb.R
import com.example.moviedb.base.BaseFragment
import com.example.moviedb.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    companion object {
        const val ARGUMENT_MOVIE = "ARGUMENT_MOVIE"
        const val ARGUMENT_TITLE = "ARGUMENT_TITLE"
    }

    override val viewModel: DetailViewModel by viewModel()

    override fun initComponentOnActivityCreated(
        viewBinding: ViewDataBinding,
        savedInstanceState: Bundle?
    ) {

        val safeArgs: DetailFragmentArgs by navArgs()
        val movie = safeArgs.ARGUMENTMOVIE

        viewModel.movie.value = movie
        viewModel.checkFavorite()
    }

    override fun getLayoutResource(): Int = R.layout.fragment_detail
}