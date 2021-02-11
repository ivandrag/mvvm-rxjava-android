package ro.dragosivanov.domain.repository

import io.reactivex.rxjava3.core.Single
import ro.dragosivanov.domain.datasource.UserRemoteDataSource
import ro.dragosivanov.domain.mapper.toListOfUsers
import ro.dragosivanov.domain.model.CreateUser
import ro.dragosivanov.domain.model.UsersResponse

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource
) {
    fun getUsers() = userRemoteDataSource.getUsers()
        .flatMap { Single.just(it.meta.pagination.pages) }
        .flatMap(::getLastPage)
        .flatMap(::toListOfUsers)

    fun addUser(name: String, email: String) =
        userRemoteDataSource.createUser(CreateUser(name, email))

    private fun getLastPage(page: Int): Single<UsersResponse> {
        return userRemoteDataSource.getLastPage(page)
    }

    private fun toListOfUsers(usersResponse: UsersResponse) =
        Single.just(usersResponse.toListOfUsers())
}
