package ro.dragosivanov.domain.utils

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers


class AppScheduler : Scheduler() {
    fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun createWorker(): Worker {
        TODO("Not yet implemented")
    }
}