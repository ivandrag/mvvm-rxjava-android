package ro.dragosivanov.ui.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ro.dragosivanov.MainApplication
import ro.dragosivanov.R
import ro.dragosivanov.di.UserListViewModelFactory

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private lateinit var userListViewModel: UserListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appContainer = (activity?.application as MainApplication).appContainer
        val userUseCase = appContainer.userContainer.userUseCase
        val userListViewModelFactory = UserListViewModelFactory(userUseCase)
        userListViewModel =
            ViewModelProvider(this, userListViewModelFactory).get(UserListViewModel::class.java)
        userListViewModel.getAllUsers()
        println(userListViewModel)
    }
}
