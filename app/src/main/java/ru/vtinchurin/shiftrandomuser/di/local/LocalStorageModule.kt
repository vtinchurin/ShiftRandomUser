package ru.vtinchurin.shiftrandomuser.di.local

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import ru.vtinchurin.shiftrandomuser.R
import ru.vtinchurin.shiftrandomuser.core.local.dao.UserDao
import ru.vtinchurin.shiftrandomuser.core.local.db.UserDatabase

val localStorageModule = module {
    single<UserDao> {
        val context: Context = get()
        val db = Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            context.getString(R.string.app_name)
        ).build()
        db.userDao()
    }
}