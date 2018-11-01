package com.tests.alsingr.posters.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.tests.alsingr.posters.data.domain.Poster
import com.tests.alsingr.posters.utilities.AppExecutors
import com.tests.alsingr.posters.utilities.LiveDataCallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * REST API access points
 */
interface PosterService {

    @GET("technical-test.json")
    fun getPosters(): LiveData<ApiResponse<List<Poster>>>


    companion object {
        @Volatile private var instance: PosterService? = null

        private const val BASE_URL = "https://img8.leboncoin.fr/"
        fun create(): PosterService = create(HttpUrl.parse(BASE_URL)!!)
        fun create(httpUrl: HttpUrl): PosterService {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Timber.w("PostService", it)
            })
            //logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(PosterService::class.java)
        }

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: create().also { instance = it }
            }
    }

}