package com.project.elt.di.module

import android.content.Context
import android.content.SharedPreferences
import com.project.elt.BuildConfig.BASE_URL
import com.project.elt.data.api.ApiHelper
import com.project.elt.data.api.ApiHelperImpl
import com.project.elt.data.api.ApiInterface
import com.project.elt.data.repository.MainRepository
import com.robertlevonyan.views.chip.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface AppModule {
    val baseUrl: String
    val okHttpClient: OkHttpClient
    val retrofit: Retrofit
    val apiservice: ApiInterface
    val apiHelper: ApiHelper
    val sharedPreferences: SharedPreferences
    val sharedPreferencesEditor: SharedPreferences.Editor
    val mainRepository: MainRepository
}

class AppModuleImpl(val appContext: Context) : AppModule {
    override val baseUrl: String = BASE_URL

    override val okHttpClient: OkHttpClient by lazy {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES).build()
        } else
            OkHttpClient.Builder().build()
    }

    override val retrofit: Retrofit by lazy {
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl)
            .client(okHttpClient).build()
    }

    override val apiservice: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }

    override val apiHelper: ApiHelper by lazy {
        ApiHelperImpl(apiservice)
    }


    override val sharedPreferences: SharedPreferences by lazy {
        appContext.getSharedPreferences("ELT", Context.MODE_PRIVATE)
    }

    override val sharedPreferencesEditor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override val mainRepository: MainRepository by lazy {
        MainRepository(apiHelper)
    }

}