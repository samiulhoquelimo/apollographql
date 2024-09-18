package com.droidturbo.apollographql.utils.extension

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager

fun Dialog?.wrap() = this?.window?.apply {
    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setLayout(
        /* width = */ 9 * context.resources.displayMetrics.widthPixels / 10,
        /* height = */ WindowManager.LayoutParams.WRAP_CONTENT
    )
}
