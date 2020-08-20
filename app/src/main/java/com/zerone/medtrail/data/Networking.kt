package com.zerone.medtrail.data

import androidx.core.os.BuildCompat
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Networking {

    private const val NETWORK_CALL_TIMEOUT = 60


    fun createNetworkCall(): MedTrailNetworkService {
        return Retrofit.Builder()
            .baseUrl(com.zerone.medtrail.BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MedTrailNetworkService::class.java)
    }

}