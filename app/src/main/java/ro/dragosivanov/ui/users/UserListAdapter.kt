package ro.dragosivanov.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.dragosivanov.R
import ro.dragosivanov.ui.users.model.User

class UserListAdapter(private val onLongItemClick: (Long) -> Unit) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var allUsersList = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false),
        onLongItemClick
    )

    override fun getItemCount() = allUsersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = allUsersList[position]
        holder.bind(user)
    }

    class ViewHolder(private val view: View, private val onLongItemClick: (Long) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val nameTextView = view.findViewById<TextView>(R.id.name_textView)
        private val emailTextView = view.findViewById<TextView>(R.id.email_textView)
        private val dateTextView = view.findViewById<TextView>(R.id.date_textView)

        fun bind(user: User) {
            nameTextView.text = user.name
            emailTextView.text = user.email
            dateTextView.text = String.format(
                view.context.getString(R.string.user_list_adapter_created_text),
                user.dateCreatedAt
            )
            view.setOnLongClickListener {
                onLongItemClick(user.id)
                true
            }
        }
    }
}
