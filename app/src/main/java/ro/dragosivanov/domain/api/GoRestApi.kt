package ro.dragosivanov.domain.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ro.dragosivanov.domain.model.UsersResponse

interface GoRestApi {

    @GET("/public-api/users")
    fun getUsers(@Query("page") pagination: Int = 0): Call<UsersResponse>
}
