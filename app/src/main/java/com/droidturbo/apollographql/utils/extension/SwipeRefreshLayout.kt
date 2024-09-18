package com.droidturbo.apollographql.utils.extension

import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.droidturbo.apollographql.R

internal fun SwipeRefreshLayout.clicked(
    block: () -> Unit
) {
    val p1 = ContextCompat.getColor(context, R.color.md_theme_tertiary)
    val p2 = ContextCompat.getColor(context, R.color.md_theme_onPrimaryFixed)
    with(this) {
        setColorSchemeColors(p1, p2)
        setOnRefreshListener { block() }
    }
}

fun SwipeRefreshLayout.loading(isLoading: Boolean = true) {
    isRefreshing = isLoading
}