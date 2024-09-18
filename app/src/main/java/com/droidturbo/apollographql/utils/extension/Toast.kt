package com.droidturbo.apollographql.utils.extension

import android.content.Context
import android.widget.Toast
import com.droidturbo.apollographql.ui.base.BaseDialogFragment
import com.droidturbo.apollographql.ui.base.BaseFragment
import com.droidturbo.apollographql.utils.UiText

fun Context.getResString(uiText: UiText): String = uiText.asString(this)

fun Context.toast(uiText: UiText) {
    Toast.makeText(this, getResString(uiText), Toast.LENGTH_SHORT).show()
}

fun BaseDialogFragment.toast(uiText: UiText) = requireContext().toast(uiText)
fun BaseFragment.toast(uiText: UiText) = requireContext().toast(uiText)
