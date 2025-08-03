package ru.vtinchurin.shiftrandomuser.core.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vtinchurin.shiftrandomuser.core.local.dao.UserDao
import ru.vtinchurin.shiftrandomuser.core.local.entity.ImageCache
import ru.vtinchurin.shiftrandomuser.core.local.entity.UserCache

@Database(entities = [UserCache::class, ImageCache::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}