package ro.dragosivanov.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ro.dragosivanov.domain.usecase.UserUseCase
import ro.dragosivanov.ui.users.model.User

class UserListViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _onEvent = MutableLiveData<OnEvent>()

    val onEvent: LiveData<OnEvent>
        get() = _onEvent

    fun getAllUsers() {
        _onEvent.value = OnEvent.ShowLoading
        disposable.add(
            userUseCase.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(::hideLoading)
                .subscribe(
                    { userList -> onUsersSuccess(userList) },
                    { throwable -> onError(throwable) }
                )
        )
    }

    fun deleteUser(id: Long) {
        disposable.add(
            userUseCase.deleteUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(::hideLoading)
                .subscribe({
                    _onEvent.value = OnEvent.UserDeletedSuccess
                }, {
                    _onEvent.value = OnEvent.UserDeletedError
                })
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    private fun onUsersSuccess(usersList: List<User>) {
        if (usersList.isEmpty()) {
            _onEvent.value = OnEvent.EmptyList
            return
        }

        _onEvent.value = OnEvent.Success(usersList)
    }

    private fun onError(it: Throwable) {
        _onEvent.value = OnEvent.Error(it.message)
    }

    private fun hideLoading() {
        _onEvent.value = OnEvent.HideLoading
    }

    sealed class OnEvent {
        object ShowLoading : OnEvent()
        object HideLoading : OnEvent()
        data class Error(val message: String?) : OnEvent()
        data class Success(val userList: List<User>) : OnEvent()
        object EmptyList : OnEvent()
        object UserDeletedSuccess: OnEvent()
        object UserDeletedError: OnEvent()
    }
}
