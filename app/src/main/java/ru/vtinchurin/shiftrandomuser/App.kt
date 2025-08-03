package ru.vtinchurin.shiftrandomuser

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.vtinchurin.shiftrandomuser.detail.di.detailModule
import ru.vtinchurin.shiftrandomuser.di.local.localStorageModule
import ru.vtinchurin.shiftrandomuser.di.network.networkModule
import ru.vtinchurin.shiftrandomuser.list.di.userListModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(
                level = Level.DEBUG
            )
            androidContext(this@App)
            modules(
                networkModule,
                localStorageModule,
                userListModule,
                detailModule,
            )
        }
    }

}