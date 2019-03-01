package com.example.moviedb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.moviedb.BR

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var viewBinding: VB
    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        viewBinding.root.isClickable = true
        viewBinding.root.setBackgroundColor(resources.getColor(android.R.color.white))
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.setVariable(BR.viewModel, viewModel)
        viewBinding.executePendingBindings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initComponentOnActivityCreated(viewBinding)
        lifecycle.addObserver(viewModel)
    }

    fun addFragment(
        fragment: Fragment,
        container: Int,
        isAddToBackStack: Boolean = true,
        tag: String
    ) {
        childFragmentManager.beginTransaction().apply {
            add(container, fragment)
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    fun replaceFragment(
        fragment: Fragment,
        container: Int,
        isAddToBackStack: Boolean = true,
        tag: String
    ) {
        childFragmentManager.beginTransaction().apply {
            replace(container, fragment)
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    protected fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    abstract fun initComponentOnActivityCreated(viewBinding: ViewDataBinding)

    @LayoutRes
    abstract fun getLayoutResource(): Int
}