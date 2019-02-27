package com.example.moviedb.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

abstract class BaseActivity<VB: ViewDataBinding, VM: BaseViewModel>: AppCompatActivity() {

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

    fun addFragment(fragment: Fragment, container: Int, isAddToBackStack: Boolean, tag: String) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(container, fragment)
        if (isAddToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, container: Int, isAddToBackStack: Boolean, tag: String) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(container, fragment)
        if (isAddToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    abstract fun initComponentOnCreate(viewBinding: VB)

    @LayoutRes
    abstract fun getLayoutResource(): Int
}