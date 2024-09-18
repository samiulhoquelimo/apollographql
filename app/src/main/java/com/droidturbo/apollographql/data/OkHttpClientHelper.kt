package com.droidturbo.apollographql.data

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.droidturbo.apollographql.utils.extension.debugMode
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientHelper(
    private val chuckerInterceptor: ChuckerInterceptor
) {

    operator fun invoke(): OkHttpClient {
        return client()
    }

    private fun client(): OkHttpClient =
        with(OkHttpClient().newBuilder()) {
            debugMode {
                val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                addInterceptor(chuckerInterceptor)
                addInterceptor(httpLoggingInterceptor)
            }
            hostnameVerifier { _, _ -> true }
            retryOnConnectionFailure(false)
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            build()
        }
}