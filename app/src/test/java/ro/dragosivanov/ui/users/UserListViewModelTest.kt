package ro.dragosivanov.ui.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ro.dragosivanov.domain.usecase.UserUseCase
import ro.dragosivanov.ui.users.model.User
import ro.dragosivanov.utils.RxImmediateSchedulerRule

@RunWith(JUnit4::class)
class UserListViewModelTest {

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val userUseCase: UserUseCase = mockk()
    private val observer: Observer<UserListViewModel.OnEvent> = mockk()

    private val userListViewModel = UserListViewModel(userUseCase)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userListViewModel.onEvent.observeForever(observer)
    }

    @Test
    fun `getAllUsers Given response is success Then show the list of users`() {
        val userList = mutableListOf<User>()
        userList.add(User(1, "name", "email", "createdAt"))
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.getUsers() } returns Single.just(userList)

        userListViewModel.getAllUsers()

        verify {
            observer.onChanged(UserListViewModel.OnEvent.ShowLoading)
            observer.onChanged(UserListViewModel.OnEvent.Success(userList))
            observer.onChanged(UserListViewModel.OnEvent.HideLoading)
        }
    }

    @Test
    fun `getAllUsers Given response has empty list Then show the list of users`() {
        val userList = mutableListOf<User>()
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.getUsers() } returns Single.just(userList)

        userListViewModel.getAllUsers()

        verify {
            observer.onChanged(UserListViewModel.OnEvent.ShowLoading)
            observer.onChanged(UserListViewModel.OnEvent.EmptyList)
            observer.onChanged(UserListViewModel.OnEvent.HideLoading)
        }
    }

    @Test
    fun `getAllUsers Given response is not successful Then show error`() {
        val exception = Exception()
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.getUsers() } returns Single.error(exception)

        userListViewModel.getAllUsers()

        verify {
            observer.onChanged(UserListViewModel.OnEvent.ShowLoading)
            observer.onChanged(UserListViewModel.OnEvent.Error(exception.message))
            observer.onChanged(UserListViewModel.OnEvent.HideLoading)
        }
    }

    @Test
    fun `deleteUser Given response is successful Then show UserDeletedSuccess`() {
        every { userUseCase.deleteUser(1) } returns Completable.complete()
        every { observer.onChanged(any()) } just Runs

        userListViewModel.deleteUser(1)

        verify {
            observer.onChanged(UserListViewModel.OnEvent.UserDeletedSuccess)
        }
    }

    @Test
    fun `deleteUser Given response is successful Then show UserDeletedError`() {
        every { userUseCase.deleteUser(1) } returns Completable.error(Exception())
        every { observer.onChanged(any()) } just Runs

        userListViewModel.deleteUser(1)

        verify {
            observer.onChanged(UserListViewModel.OnEvent.UserDeletedError)
        }
    }
}
