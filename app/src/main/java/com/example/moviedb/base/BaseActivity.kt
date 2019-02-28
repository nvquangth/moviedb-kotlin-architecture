package com.example.moviedb.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    private lateinit var viewBinding: VB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, getLayoutResource())
        viewBinding.lifecycleOwner = this
        initComponentOnCreate(viewBinding)
    }

    protected fun toast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun addFragment(
        fragment: Fragment,
        container: Int,
        isAddToBackStack: Boolean = true,
        tag: String
    ) {
        supportFragmentManager.beginTransaction().apply {
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
        supportFragmentManager.beginTransaction().apply {
            replace(container, fragment)
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    abstract fun initComponentOnCreate(viewBinding: VB)

    @LayoutRes
    abstract fun getLayoutResource(): Int
}