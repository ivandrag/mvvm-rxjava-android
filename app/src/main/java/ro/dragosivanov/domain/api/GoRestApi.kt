package ro.dragosivanov.domain.api

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import ro.dragosivanov.domain.model.CreateUser
import ro.dragosivanov.domain.model.UsersResponse

interface GoRestApi {

    @GET("/public-api/users")
    fun getUsers(@Query("page") page: Int = 0): Single<UsersResponse>

    @POST("/public-api/users")
    fun createUser(@Body user: CreateUser): Completable

    @DELETE("/public-api/users/{id}")
    fun deleteUser(@Path("id") id: Long): Completable

}
