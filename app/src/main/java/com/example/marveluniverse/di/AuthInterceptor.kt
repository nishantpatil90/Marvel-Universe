package com.example.marveluniverse.di

import com.example.marveluniverse.utils.PRIVATE_KEY
import com.example.marveluniverse.utils.PUBLIC_KEY
import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val ts = System.currentTimeMillis()

        val hash = "$ts$PRIVATE_KEY$PUBLIC_KEY".md5()
        val request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter("ts", ts.toString())
            .addQueryParameter("apikey", PUBLIC_KEY)
            .addQueryParameter("hash", hash)
            .build()
        val updated = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(updated)
    }

    private fun String.md5(): String =
        BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
            .toString(16).padStart(32, '0')
}
