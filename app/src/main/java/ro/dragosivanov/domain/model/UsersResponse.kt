package ro.dragosivanov.domain.model

data class UsersResponse(
    val code: Int,
    val meta: Meta,
    val data: List<User>
)
