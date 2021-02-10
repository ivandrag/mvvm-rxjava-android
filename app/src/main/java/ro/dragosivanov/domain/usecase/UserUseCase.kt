package ro.dragosivanov.domain.usecase

import ro.dragosivanov.domain.repository.UserRepository

class UserUseCase(
    private val userRepository: UserRepository
) {
    fun getUsers() = userRepository.getUsers()

}