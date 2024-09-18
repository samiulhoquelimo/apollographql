package com.droidturbo.apollographql.utils.extension

import com.droidturbo.apollographql.BuildConfig

inline fun debugMode(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}