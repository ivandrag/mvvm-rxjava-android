package ro.dragosivanov

import android.app.Application
import ro.dragosivanov.di.AppContainer

class MainApplication: Application() {
    val appContainer = AppContainer()
}
