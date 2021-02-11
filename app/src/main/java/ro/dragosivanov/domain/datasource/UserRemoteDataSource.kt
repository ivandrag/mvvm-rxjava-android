package ro.dragosivanov.domain.datasource

import ro.dragosivanov.domain.api.GoRestApi
import ro.dragosivanov.domain.model.CreateUser

class UserRemoteDataSource(
    private val goRestApi: GoRestApi
) {
    fun getUsers() = goRestApi.getUsers()

    fun getLastPage(page: Int) = goRestApi.getUsers(page)

    fun createUser(user: CreateUser) = goRestApi.createUser(user)
}
