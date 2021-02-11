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

data class CreateUser(
    val name: String,
    val email: String,
    // Added this as default, because without them it was not adding the user
    val gender: String = "Male",
    val status: String = "Active"
)
