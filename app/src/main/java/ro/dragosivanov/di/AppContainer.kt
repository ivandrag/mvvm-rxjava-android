package ro.dragosivanov.di

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
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
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
