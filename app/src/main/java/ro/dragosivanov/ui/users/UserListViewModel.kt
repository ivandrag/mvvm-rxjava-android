package ro.dragosivanov.ui.users

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ro.dragosivanov.domain.usecase.UserUseCase

class UserListViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()

    fun getAllUsers() {
        disposable.add(
            userUseCase.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    println("## ${response.data}")
                }
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
