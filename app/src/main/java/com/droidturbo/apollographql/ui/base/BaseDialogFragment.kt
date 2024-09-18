package com.droidturbo.apollographql.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import com.droidturbo.apollographql.utils.extension.wrap

abstract class BaseDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

    override fun onResume() {
        super.onResume()
        dialog.wrap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        observeViewModel()
    }

    abstract fun setup(view: View)

    protected open fun observeViewModel(): Unit = Unit

    protected open fun dev(): Unit = Unit
}