package ro.dragosivanov.domain.mapper

import android.text.format.DateUtils
import ro.dragosivanov.domain.model.UsersResponse
import ro.dragosivanov.ui.users.model.User
import java.text.SimpleDateFormat
import java.util.*

fun UsersResponse.toListOfUsers(): List<User> {
    val usersList = mutableListOf<User>()
    val currentTime = Calendar.getInstance().timeInMillis
    for (user in this.data) {
        val relativeTime =
            DateUtils.getRelativeTimeSpanString(
                convertDateToLong(user.created_at) ?: 0L,
                currentTime,
                0L,
                DateUtils.FORMAT_ABBREV_TIME
            ).toString()

        usersList.add(User(user.id, user.name, user.email, relativeTime))
    }
    return usersList
}

fun convertDateToLong(date: String): Long? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    return dateFormat.parse(date).time
}
