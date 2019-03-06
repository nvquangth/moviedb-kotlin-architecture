package com.example.moviedb.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    private lateinit var viewBinding: VB
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, getLayoutResource())
        viewBinding.lifecycleOwner = this
        initComponentOnCreate(viewBinding, savedInstanceState)
    }

    protected fun toast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showBackActionbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun hideBackActionbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun addFragment(
        fragment: Fragment,
        container: Int,
        isAddToBackStack: Boolean = true,
        tag: String
    ) {
        supportFragmentManager.beginTransaction().apply {
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
        supportFragmentManager.beginTransaction().apply {
            replace(container, fragment, tag)
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    fun findFragmentByTag(tag: String) = supportFragmentManager.findFragmentByTag(tag)

    abstract fun initComponentOnCreate(viewBinding: VB, savedInstanceState: Bundle?)

    @LayoutRes
    abstract fun getLayoutResource(): Int
}