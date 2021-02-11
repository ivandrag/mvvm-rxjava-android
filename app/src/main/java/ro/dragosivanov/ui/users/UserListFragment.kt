package ro.dragosivanov.ui.users

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ro.dragosivanov.MainApplication
import ro.dragosivanov.R
import ro.dragosivanov.di.AppContainer
import ro.dragosivanov.di.UserContainer
import ro.dragosivanov.di.UserListViewModelFactory
import ro.dragosivanov.ui.BaseFragment

class UserListFragment : BaseFragment(R.layout.fragment_user_list) {

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var userListRecyclerView: RecyclerView
    private lateinit var addUserButton: FloatingActionButton
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var appContainer: AppContainer

    private val onEventObserver = Observer<UserListViewModel.OnEvent> {
        when (it) {
            UserListViewModel.OnEvent.ShowLoading -> showLoading()
            UserListViewModel.OnEvent.HideLoading -> hideLoading()
            is UserListViewModel.OnEvent.Error -> Toast.makeText(
                activity,
                getString(R.string.user_list_fragment_error_text),
                Toast.LENGTH_LONG
            ).show()
            is UserListViewModel.OnEvent.Success -> {
                userListRecyclerView.layoutManager = LinearLayoutManager(activity)
                userListRecyclerView.adapter = userListAdapter
                userListAdapter.allUsersList.addAll(it.userList.asReversed())
            }
            UserListViewModel.OnEvent.EmptyList -> Toast.makeText(
                activity,
                getString(R.string.user_list_fragment_empty_list_text),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListAdapter = UserListAdapter()
        initViews()
        userListViewModel =
            ViewModelProvider(this, initDi()).get(UserListViewModel::class.java)
        userListViewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)
        userListViewModel.getAllUsers()
        addUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        appContainer.userContainer = null
    }

    private fun addUser() {
        addUserButton.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUserDialogFragment)
        }
    }

    private fun initViews() {
        userListRecyclerView = requireActivity().findViewById(R.id.user_list_recyclerView)
        addUserButton = requireActivity().findViewById(R.id.add_user_button)
    }

    private fun initDi(): UserListViewModelFactory {
        appContainer = (activity?.application as MainApplication).appContainer
        val userContainer = UserContainer(appContainer.goRestApi)
        val userUseCase = userContainer.userUseCase
        return UserListViewModelFactory(userUseCase)
    }
}
