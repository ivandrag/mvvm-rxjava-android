package ro.dragosivanov.domain.api

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ro.dragosivanov.domain.model.CreateUser
import ro.dragosivanov.domain.model.UsersResponse

interface GoRestApi {

    @GET("/public-api/users")
    fun getUsers(@Query("page") page: Int = 0): Single<UsersResponse>

    @POST("/public-api/users")
    fun createUser(@Body user: CreateUser): Completable
}
