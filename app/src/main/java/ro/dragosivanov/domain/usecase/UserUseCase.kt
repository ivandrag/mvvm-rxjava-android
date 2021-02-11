package ro.dragosivanov.domain.usecase

import ro.dragosivanov.domain.repository.UserRepository

class UserUseCase(
    private val userRepository: UserRepository
) {
    fun getUsers() = userRepository.getUsers()

    fun addUser(name: String, email: String) = userRepository.addUser(name, email)
}
