package ro.dragosivanov.domain.repository

import ro.dragosivanov.domain.datasource.UserRemoteDataSource

class UserRepository(
    private val userRemoteDataSource: UserRemoteDataSource
) {
}