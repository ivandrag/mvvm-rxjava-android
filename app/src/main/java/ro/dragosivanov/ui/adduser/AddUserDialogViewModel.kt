package ro.dragosivanov.ui.adduser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ro.dragosivanov.domain.usecase.UserUseCase

class AddUserDialogViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _onEvent = MutableLiveData<OnEvent>()

    val onEvent: LiveData<OnEvent>
        get() = _onEvent

    fun createUser(name: String?, email: String?) {
        when {
            name.isNullOrBlank() -> _onEvent.value = OnEvent.EmptyName
            email.isNullOrBlank() -> _onEvent.value = OnEvent.EmptyEmail
            else -> {
                disposable.add(
                    userUseCase.addUser(name, email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            _onEvent.value = OnEvent.Success
                        }, {
                            _onEvent.value = OnEvent.Error
                        })

                )
            }
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    sealed class OnEvent {
        object Success : OnEvent()
        object Error : OnEvent()
        object EmptyName : OnEvent()
        object EmptyEmail : OnEvent()
    }
}
