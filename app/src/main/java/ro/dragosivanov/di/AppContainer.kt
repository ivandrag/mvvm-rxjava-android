package ro.dragosivanov.di

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ro.dragosivanov.domain.api.GoRestApiInterceptor

class AppContainer {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(GoRestApiInterceptor())
        .build()

    val retrofitApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://gorest.co.in/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
