package ro.dragosivanov.domain.model

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val gender: String,
    val status: String,
    val created_at: String,
    val updated_at: String
)
