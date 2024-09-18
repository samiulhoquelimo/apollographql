package com.droidturbo.apollographql.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun getLayoutResourceId(): View?

    protected open fun initView(): Unit = Unit

    protected open fun observeViewModel(): Unit = Unit

    protected open var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        getLayoutResourceId()?.let { setContentView(it) }
        initView()
        observeViewModel()
    }
}