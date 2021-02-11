package ro.dragosivanov.ui.adduser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ro.dragosivanov.MainApplication
import ro.dragosivanov.R
import ro.dragosivanov.di.AddListViewModelFactory
import ro.dragosivanov.di.AddUserContainer
import ro.dragosivanov.di.AppContainer

class AddUserDialogFragment : BottomSheetDialogFragment() {

    private lateinit var appContainer: AppContainer
    private lateinit var addUserDialogViewModel: AddUserDialogViewModel
    private lateinit var addUserButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText

    private val onEventObserver = Observer<AddUserDialogViewModel.OnEvent> {
        when (it) {
            AddUserDialogViewModel.OnEvent.Success -> findNavController().popBackStack()
            AddUserDialogViewModel.OnEvent.Error -> Toast.makeText(
                activity,
                getString(R.string.add_user_error_text),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.dialog_fragment_add_user,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addUserDialogViewModel =
            ViewModelProvider(this, initDi()).get(AddUserDialogViewModel::class.java)
        addUserDialogViewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)
        addUserButton = requireActivity().findViewById(R.id.create_user_button)
        nameEditText = requireActivity().findViewById(R.id.name_edit_text)
        emailEditText = requireActivity().findViewById(R.id.email_edit_text)
        addUserButton.setOnClickListener {
            addUserDialogViewModel.createUser(
                nameEditText.text.toString(),
                emailEditText.text.toString()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appContainer.addUserContainer = null
    }

    private fun initDi(): AddListViewModelFactory {
        appContainer = (activity?.application as MainApplication).appContainer
        val addUserContainer = AddUserContainer(appContainer.goRestApi)
        val userUseCase = addUserContainer.userUseCase
        return AddListViewModelFactory(userUseCase)
    }
}
