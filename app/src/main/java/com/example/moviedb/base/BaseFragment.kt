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
        return viewBinding.root.apply {
            isClickable = true
            setBackgroundColor(resources.getColor(android.R.color.white))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
            executePendingBindings()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initComponentOnActivityCreated(viewBinding, savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    fun addFragmentToActivity(
        fragment: Fragment,
        container: Int,
        isAddToBackStack: Boolean,
        tag: String
    ) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            add(container, fragment, tag)
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    fun replaceFragmentToActivity(
        fragment: Fragment,
        container: Int,
        isAddToBackStack: Boolean,
        tag: String
    ) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(container, fragment, tag)
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    fun addFragment(
        fragment: Fragment,
        container: Int,
        isAddToBackStack: Boolean = true,
        tag: String
    ) {
        childFragmentManager.beginTransaction().apply {
            add(container, fragment, tag)
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
            replace(container, fragment, tag)
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    fun findFragmentByTag(tag: String) = childFragmentManager.findFragmentByTag(tag)

    protected fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    abstract fun initComponentOnActivityCreated(
        viewBinding: ViewDataBinding,
        savedInstanceState: Bundle?
    )

    @LayoutRes
    abstract fun getLayoutResource(): Int
}