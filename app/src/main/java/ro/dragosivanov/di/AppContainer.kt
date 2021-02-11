package ro.dragosivanov.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ro.dragosivanov.domain.api.GoRestApi
import ro.dragosivanov.domain.api.GoRestApiInterceptor
import ro.dragosivanov.domain.datasource.UserRemoteDataSource
import ro.dragosivanov.domain.repository.UserRepository
import ro.dragosivanov.domain.usecase.UserUseCase

class AppContainer {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(GoRestApiInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://gorest.co.in/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val goRestApi = retrofit.create(GoRestApi::class.java)
    var userContainer : UserContainer? = null
    var addUserContainer: AddUserContainer? = null
}

class UserContainer(goRestApi: GoRestApi) {
    private val userRemoteDataSource = UserRemoteDataSource(goRestApi)
    private val userRepository = UserRepository(userRemoteDataSource)
    val userUseCase = UserUseCase(userRepository)
}

class AddUserContainer(goRestApi: GoRestApi) {
    private val userRemoteDataSource = UserRemoteDataSource(goRestApi)
    private val userRepository = UserRepository(userRemoteDataSource)
    val userUseCase = UserUseCase(userRepository)
}

class UserListViewModelFactory(private val userUseCase: UserUseCase?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserUseCase::class.java).newInstance(userUseCase)
    }
}

class AddListViewModelFactory(private val userUseCase: UserUseCase?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserUseCase::class.java).newInstance(userUseCase)
    }
}