package ro.dragosivanov.domain.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ro.dragosivanov.domain.model.UsersResponse

interface GoRestApi {

    @GET("/public-api/users")
    fun getUsers(@Query("page") pagination: Int = 0): Single<UsersResponse>
}
