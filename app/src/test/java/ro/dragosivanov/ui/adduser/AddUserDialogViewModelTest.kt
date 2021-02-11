package ro.dragosivanov.ui.adduser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.reactivex.rxjava3.core.Completable
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import ro.dragosivanov.domain.usecase.UserUseCase
import ro.dragosivanov.ui.users.UserListViewModel
import ro.dragosivanov.utils.RxImmediateSchedulerRule
import java.lang.Exception

class AddUserDialogViewModelTest {

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val userUseCase: UserUseCase = mockk()
    private val observer: Observer<AddUserDialogViewModel.OnEvent> = mockk()

    private val addUserDialogViewModel = AddUserDialogViewModel(userUseCase)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addUserDialogViewModel.onEvent.observeForever(observer)
    }

    @Test
    fun `createUser Given response is success Then show success`() {
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.addUser("name", "email") } returns Completable.complete()

        addUserDialogViewModel.createUser("name", "email")

        verify {
            observer.onChanged(AddUserDialogViewModel.OnEvent.Success)
        }
    }

    @Test
    fun `createUser Given response is not successful Then show error`() {
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.addUser("name", "email") } returns Completable.error(Exception())

        addUserDialogViewModel.createUser("name", "email")

        verify {
            observer.onChanged(AddUserDialogViewModel.OnEvent.Error)
        }
    }

    @Test
    fun `createUser Given name is null Then show name error`() {
        every { observer.onChanged(any()) } just Runs

        addUserDialogViewModel.createUser(null, "email")

        verify {
            observer.onChanged(AddUserDialogViewModel.OnEvent.EmptyName)
        }
    }

    @Test
    fun `createUser Given name is empty Then show name error`() {
        every { observer.onChanged(any()) } just Runs

        addUserDialogViewModel.createUser("", "email")

        verify {
            observer.onChanged(AddUserDialogViewModel.OnEvent.EmptyName)
        }
    }

    @Test
    fun `createUser Given email is null Then show email error`() {
        every { observer.onChanged(any()) } just Runs

        addUserDialogViewModel.createUser("name", null)

        verify {
            observer.onChanged(AddUserDialogViewModel.OnEvent.EmptyEmail)
        }
    }

    @Test
    fun `createUser Given email is empty Then show email error`() {
        every { observer.onChanged(any()) } just Runs

        addUserDialogViewModel.createUser("name", "")

        verify {
            observer.onChanged(AddUserDialogViewModel.OnEvent.EmptyEmail)
        }
    }
}