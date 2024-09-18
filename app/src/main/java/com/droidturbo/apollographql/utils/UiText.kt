package com.droidturbo.apollographql.utils

import android.content.Context
import androidx.annotation.StringRes
import com.droidturbo.apollographql.R
import java.io.Serializable

sealed class UiText : Serializable {
    data class DynamicString(val value: String?) : UiText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value ?: context.getString(R.string.api_default_error)
            is StringResource -> context.getString(resId, *args)
        }
    }
}
